package smarthome;

import io.javalin.Javalin;

public class ExampleModule {
    public ExampleModule(Javalin app) {
        app.get("/example", ctx -> ctx.result("This is an example"));
    }
}
