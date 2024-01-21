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
        private final String color;
        private final String text;

        @JsonCreator
        public Cell(@JsonProperty("color") String color, @JsonProperty("text") String text) {
            this.color = color;
            this.text = text;
        }

        public Cell(String color) {
            this(color, "");
        }

        public Cell() {
            this("000000", "");
        }

        public String getColor() {
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

        private final Cell[][] grid;

        public Grid(int xSize, int ySize) {
            grid = new Cell[xSize][ySize];
            for (int i = 0; i < xSize; ++i) {
                for (int j = 0; j < ySize; ++j) {
                    grid[i][j] = new Cell();
                }
            }
        }

        public Grid() {
            this(8, 8);
        }

        public Cell getCell(int x, int y) {
            return grid[x][y];
        }

        public Cell[][] getCells() {
            return grid;
        }

        public boolean isWithin(int x, int y) {
            return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
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
            //e.printStackTrace();
            e.getMessage();
            throw new RuntimeException("Error parsing JSON to JsonNode: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        LinkedList<Grid> grids = new LinkedList<>();
        Javalin app = Javalin.create();


        // Create an empty grid of size x and y
        app.post("/json", ctx -> {
            JsonNode jsonNode = parseJsonToNode(ctx.body());
            int xParam = jsonNode.path("x").asInt();
            int yParam = jsonNode.path("y").asInt();

            // No empty or negative values accepted
            if (xParam <= 0 || yParam <= 0) {
                ctx.status(418);
                return;
            }

            grids.add(new Grid(xParam, yParam));
            System.out.println("POST");
            ctx.status(201).result("ok");
        });

        // Modifies the cell x y of the first grid with the given color and the given text
        app.patch("/json", ctx -> {
            // Abort if there is no grid
            if (grids.isEmpty()) {
                ctx.status(418);
                return;
            }

            JsonNode jsonNode = parseJsonToNode(ctx.body());
            int xParam = jsonNode.path("x").asInt();
            int yParam = jsonNode.path("y").asInt();

            Grid grid = grids.getFirst();
            // The index must be within the current grid dimensions
            if (!grid.isWithin(xParam, yParam)) {
                ctx.status(418);
                return;
            }

            Cell cell = parseJsonToCell(jsonNode.path("cell").toString());
            grid.set(xParam, yParam, cell);
            ctx.status(200);
            System.out.println("PATCH");
        });

        // Delete the first grid
        app.delete("/json", ctx -> {
            // Abort if there is no grid to delete
            if (grids.isEmpty()) {
                ctx.status(418);
                return;
            }

            grids.removeFirst();
            System.out.println("DELETE");
            ctx.status(204);
        });

        app.get("/json", ctx -> {
            if (grids.isEmpty()) {
                ctx.status(418);
                return;
            }

            String x = ctx.queryParam("x");
            String y = ctx.queryParam("y");
            Grid grid = grids.getFirst();
            ObjectMapper mapper = new ObjectMapper();
            String json;
            if (x != null && y != null) {
                int xParam = Integer.parseInt(x);
                int yParam = Integer.parseInt(y);
                if (!grid.isWithin(xParam, yParam)) {
                    ctx.status(418);
                    return;
                }

                json = mapper.writeValueAsString(grid.getCell(Integer.parseInt(x), Integer.parseInt(y)));
            } else if (x != null) {
                int xParam = Integer.parseInt(x);
                if (!grid.isWithin(xParam, 0)) {
                    ctx.status(418);
                    return;
                }

                json = mapper.writeValueAsString(grid.getLine(Integer.parseInt(x)));
            } else if (y != null) {
                int yParam = Integer.parseInt(y);
                if (!grid.isWithin(0, yParam)) {
                    ctx.status(418);
                    return;
                }

                json = mapper.writeValueAsString(grid.getColumn(Integer.parseInt(y)));
            } else {
                json = mapper.writeValueAsString(grid.getCells());
            }

            ctx.json(json);
            System.out.println("GET");
        });

        app.start(PORT);
    }
}