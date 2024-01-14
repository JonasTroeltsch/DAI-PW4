package ch.heigvd;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.*;

import java.awt.*;
import java.io.IOException;

public class Main {
    public static final int PORT = 8080;

    private class Cell {
        private Color color;
        private String text;

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

        Javalin app = Javalin.create();
        app.post("/", ctx -> {
            String jsonPayload = ctx.body();
            JsonNode jsonNode = parseJsonToNode(jsonPayload);
            int xParam = jsonNode.path("x").asInt();
            int yParam = jsonNode.path("y").asInt();
            Cell cell = parseJsonToCell(jsonPayload);
            grid[xParam][yParam] = cell;
            System.out.println("Received JSON payload: " + cell);
        });

        app.get("/", ctx -> {
            ctx.json(grid);
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