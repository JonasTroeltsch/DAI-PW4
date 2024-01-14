package ch.heigvd;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.*;

import java.awt.*;
import java.io.IOException;

public class Main {
    public static final int PORT = 8080;

    private static class Cell {
        private final Color color;
        private final String text;

        public Cell(Color color, String text) {
            this.color = color;
            this.text = text;
        }

        public Cell(Color color) {
            this(color, "");
        }

        public Cell(String text) {
            this(Color.WHITE, text);
        }

        public Cell() {
            this(Color.WHITE, "");
        }

        @Override
        public String toString() {
            return "Cell{color=" + color + ", text='" + text + "'}";
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

    public static void main(String[] args) {
        int xSize = 10;
        int ySize = 10;
        Cell[][] grid = new Cell[xSize][ySize];

        // TODO this is bugged as Cell does not have any serializer
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                grid[i][j] = new Cell();
            }
        }

        Javalin app = Javalin.create();
        // for example :
        // curl -X POST -H "Content-Type: application/json" -d '{"x": 1, "y": 2, "cell": {"color": {"red": 255, "green": 0, "blue": 0}, "text": "Hello, world!"}}' http://localhost:8080/json
        app.post("/json", ctx -> {
            String jsonPayload = ctx.body();
            JsonNode jsonNode = parseJsonToNode(jsonPayload);
            int xParam = jsonNode.path("x").asInt();
            int yParam = jsonNode.path("y").asInt();
            Cell cell = parseJsonToCell(jsonNode.path("cell").toString());
            grid[xParam][yParam] = cell;
            System.out.println("post JSON : " + cell);
        });

        // for example : http://localhost:8080
        // /json?x=1&y=2
        // /json?x=1
        // /json?y=2
        // /json
        app.get("/json", ctx -> {
            String x = ctx.queryParam("x");
            String y = ctx.queryParam("y");
            if (x != null && y != null) {
                System.out.println("x : " + x + " y : " + y);
                ctx.json(grid[Integer.parseInt(x)][Integer.parseInt(y)]);
            } else if (x != null) {
                ctx.json(grid[Integer.parseInt(x)]);
            } else if (y != null) {
                int yColumn = Integer.parseInt(y);
                Cell[] column = new Cell[xSize];
                for (int i = 0; i < xSize; i++) {
                    column[i] = grid[i][yColumn];
                }
                ctx.json(column);
            } else {
                ctx.json(grid);
            }
        });

        app.get("/page1/coords", ctx -> {// example : /page1/coords?x=1&y=2
            String x = ctx.queryParam("x");
            String y = ctx.queryParam("y");

            if (x == null || y == null) {
                throw new BadRequestResponse();
            }

            ctx.result("Affichage coordonnées : (" + x + "; " + y + ")");
        });

        app.get("/page1/{path-parameter}", ctx -> {// example : /page1/test
            String pathParameter = ctx.pathParam("path-parameter");
            ctx.result("You just called `/page1` with path parameter '" + pathParameter + "'!");
        });

        app.start(PORT);
    }
}