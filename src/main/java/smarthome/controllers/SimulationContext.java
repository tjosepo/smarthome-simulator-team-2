package smarthome.controllers;

import io.javalin.Javalin;
import smarthome.models.User;
import smarthome.models.Room;

import java.util.ArrayList;

public class SimulationContext {
    HouseLayout houseLayout;
    SimulationParameters simulationParameters;

    public SimulationContext(Javalin app) {
        houseLayout = new HouseLayout(app);
        simulationParameters = new SimulationParameters(app);

        app.post("/api/move-users", ctx -> {
            ArrayList<User> users = simulationParameters.getUsers();
            for(User user : users) {
                int roomId = Integer.parseInt(ctx.formParam("locationUser" + user.getId()));
                moveUser(user.getId(), roomId);
            }
            ctx.json(users);
        });
    }

    private void moveUser(int userId , int roomId) {
        User user = simulationParameters.getUser(userId);
        Room room = houseLayout.getRoom(roomId);
        user.setLocation(room);
    }
}
