package smarthome.controllers;

import io.javalin.Javalin;
import smarthome.models.User;
import smarthome.models.Room;
import smarthome.models.Window;

import java.util.ArrayList;

/**
 * The type Simulation context.
 */
public class SimulationContext {
    /**
     * Creating the variable HouseLayout of type HouseLayout.
     */
    public HouseLayout houseLayout;
    /**
     * Creating the variable Simulation parameters of type simulationParameters.
     */
    public SimulationParameters simulationParameters;

    /**
     * Instantiates a new Simulation context.
     *
     * @param app the app
     */
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
    /**
     *Method that will be used to move a user between rooms
     *
     * */
    private void moveUser(int userId , int roomId) {
        User user = simulationParameters.getUser(userId);
        Room room = houseLayout.getRoom(roomId);
        user.setLocation(room);
    }
    /**
     * Method that will be used to block a specified window
     * */
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
