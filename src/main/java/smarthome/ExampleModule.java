package smarthome;

import io.javalin.Javalin;

/**
 * The type Example module.
 */
public class ExampleModule {
    /**
     * Instantiates a new Example module.
     *
     * @param app the app
     */
    public ExampleModule(Javalin app) {
        app.get("/example", ctx -> ctx.result("This is an example"));
    }
}
