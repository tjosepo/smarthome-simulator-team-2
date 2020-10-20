package smarthome.controllers;

import io.javalin.Javalin;
import smarthome.models.User;
import smarthome.models.Room;
import smarthome.models.Window;

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

        app.post("/api/block-windows", ctx -> {
            ArrayList<Room> rooms = houseLayout.getRooms();
            for(Room room : rooms) {
                for(Window window : room.getWindows()) {
                    boolean shouldBlockWindow = Boolean.parseBoolean(ctx.formParam("blockWindow" + window.getId()));
                    if (shouldBlockWindow) {
                        blockWindow(window.getId());
                    }
                }
            }
            ctx.json(rooms);
        });
    }

    private void moveUser(int userId , int roomId) {
        User user = simulationParameters.getUser(userId);
        Room room = houseLayout.getRoom(roomId);
        user.setLocation(room);
    }

    private void blockWindow(int id) {
        ArrayList<Room> rooms = houseLayout.getRooms();
        for(Room room : rooms) {
            for (Window window : room.getWindows()) {
                if (window.getId() == id) {
                    window.setBlocked(true);
                    break;
                }
            }
        }
    }
}
