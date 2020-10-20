package smarthome;

import io.javalin.Javalin;
import smarthome.controllers.HouseLayout;
import smarthome.controllers.SimulationContext;
import smarthome.controllers.SimulationParameters;

/**
 * Main class.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.defaultContentType = "application/json";
            config.enableCorsForAllOrigins();
        }).start(7000);
        app.get("/", ctx -> ctx.result("Hello World"));
        new ExampleModule(app);
        new SimulationContext(app);

    }
}
