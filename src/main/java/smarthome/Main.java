package smarthome;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.result("Hello World"));
        app.get("/*", ctx -> ctx.result("404: Page not: found"));
    }
}
