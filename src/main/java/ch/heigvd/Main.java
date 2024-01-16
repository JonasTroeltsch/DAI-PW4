package ch.heigvd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

public class Main {
    private static final int PORT = 7070;

    private static class Cell {
        private final Color color;
        private final String text;

        @JsonCreator
        public Cell(@JsonProperty("color") String color, @JsonProperty("text") String text) {
            this(hexToColor(color), text);
        }

        @JsonCreator
        public Cell(@JsonProperty("color") Color color, @JsonProperty("text") String text) {
            this.color = color;
            this.text = text;
        }

        @JsonCreator
        public Cell(@JsonProperty("color") String color) {
            this(color, "");
        }

        @JsonCreator
        public Cell() {
            this(Color.WHITE, "");
        }

        public Color getColor() {
            return color;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return "Cell{color=" + color + ", text='" + text + "'}";
        }
    }

    private static class Grid {

        private Cell[][] grid;
        
        public Grid(int xSize, int ySize) {
            grid = new Cell[xSize][ySize];
        }

        public Grid() {
            this(8, 8);
        }

        public Cell get(int x, int y) {
            return grid[x][y];
        }

        public void set(int x, int y, Cell cell) {
            grid[x][y] = cell;
        }

        public Cell[] getLine(int x) {
            return grid[x];
        }

        public Cell[] getColumn(int y) {
            Cell[] column = new Cell[grid.length];
            for (int i = 0; i < grid.length; i++) {
                column[i] = grid[i][y];
            }

            return column;
        }
    }

    private static Cell parseJsonToCell(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Cell.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing JSON to Cell object: " + e.getMessage());
        }
    }

    private static JsonNode parseJsonToNode(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing JSON to JsonNode: " + e.getMessage());
        }
    }

    private static Color hexToColor(String hexColor) {
        int intValue = Integer.parseInt(hexColor, 16);
        return new Color(intValue);
    }

    public static void main(String[] args) {
        LinkedList<Grid> grids = new LinkedList<>();
        Javalin app = Javalin.create();

        //TODO check variables to ensure things exist when we consult them and/or they are comprised in correct intervals

        // curl -X POST -H "Content-Type: application/json" -d '{"x": 2, "y": 3}' http://localhost:8080/json
        // Create an empty grid of size x and y
        app.post("/json", ctx -> {
            JsonNode jsonNode = parseJsonToNode(ctx.body());
            int xParam = jsonNode.path("x").asInt();
            int yParam = jsonNode.path("y").asInt();
            grids.add(new Grid(xParam, yParam));
            ctx.status(201);
        });

        // curl -X PATCH -H "Content-Type: application/json" -d '{"x": 1, "y": 2, "cell": {"color": "220022", "text": "Hello, world!"}}' http://localhost:8080/json
        // Modifies the cell x y of the first grid with the given color and the given text
        app.patch("/json", ctx -> {
            JsonNode jsonNode = parseJsonToNode(ctx.body());
            int xParam = jsonNode.path("x").asInt();
            int yParam = jsonNode.path("y").asInt();
            Cell cell = parseJsonToCell(jsonNode.path("cell").toString());
            grids.getFirst().set(xParam, yParam, cell);
            System.out.println("PATCH : " + cell);
            ctx.status(200);
        });

        // curl -X POST -H "Content-Type: application/json" http://localhost:8080/json
        // Delete the first grid
        app.delete("/json", ctx -> {
            JsonNode jsonNode = parseJsonToNode(ctx.body());
            grids.removeFirst();
            System.out.println("DELETE");
            ctx.status(204);
        });

        // for example : http://localhost:8080
        // /json?x=1&y=2
        // /json?x=1
        // /json?y=2
        // /json
        app.get("/json", ctx -> {
            String x = ctx.queryParam("x");
            String y = ctx.queryParam("y");

            ObjectMapper mapper = new ObjectMapper();
            String json;
            if (x != null && y != null) {
                json = mapper.writeValueAsString(grids.getFirst().get(Integer.parseInt(x), Integer.parseInt(y)));
            } else if (x != null) {
                json = mapper.writeValueAsString(grids.getFirst().getLine(Integer.parseInt(x)));
            } else if (y != null) {
                json = mapper.writeValueAsString(grids.getFirst().getColumn(Integer.parseInt(y)));
            } else {
                ctx.json(grids);
                json = mapper.writeValueAsString(grids);
            }

            ctx.json(json);
        });

        app.start(PORT);
    }
}