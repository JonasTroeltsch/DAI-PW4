package ch.heigvd;

import io.javalin.Javalin;
import io.javalin.http.*;

public class Main {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        Javalin app = Javalin.create();

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