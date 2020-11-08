package smarthome.controllers;

import io.javalin.Javalin;
import smarthome.interfaces.Module;
import smarthome.interfaces.Observable;
import smarthome.interfaces.Observer;
import smarthome.models.Room;
import smarthome.models.User;
import smarthome.models.Window;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Simulation context.
 */
public class SmartHomeSimulator implements Observable {
    private Javalin app;
    /**
     * Creating the variable HouseLayout of type HouseLayout.
     */
    private House house;

    /**
     * Creating the variable para of type params.
     */
    private Parameters params;

    private List<Module> modules = new LinkedList();
    private List<Observer> observers = new LinkedList();

    /**
     * Instantiates a new Simulation.
     */
    public SmartHomeSimulator() {
        app = Javalin.create(config -> {
            config.defaultContentType = "application/json";
            config.enableCorsForAllOrigins();
        }).start(7000);

        Console.createConnection(app);
        house = new House(app);
        params = new Parameters(app);

        app.post("/api/move-users", ctx -> {
            List<User> users = params.getUsers();
            for (User user : users) {
                int roomId = Integer.parseInt(ctx.formParam("locationUser" + user.getId()));
                moveUser(user.getId(), roomId);
            }
            notifyObservers(this);
            ctx.json(this);
        });

        app.post("/api/block-windows", ctx -> {
            List<Room> rooms = house.getRooms();
            for (Room room : rooms) {
                for (Window window : room.getWindows()) {
                    boolean shouldBlockWindow = Boolean.parseBoolean(ctx.formParam("blockWindow" + window.getId()));
                    if (shouldBlockWindow) {
                        blockWindow(window.getId());
                    }
                }
            }
            ctx.json(rooms);
        });
    }

    public void loadModule(Module m) {
        m.onLoad(app, this);
    }

    public Module getModule(Class classType) {
        for (Module module : modules) {
            if (module.getClass().equals(classType)) {
                return module;
            }
        }
        return null;
    }

    /**
     * Method that will be used to move a user between rooms
     */
    private void moveUser(int userId, int roomId) {
        User user = params.getUser(userId);
        Room room = house.getRoom(roomId);
        user.setLocation(room);
    }

    /**
     * Method that will be used to block a specified window
     */
    private void blockWindow(int id) {
        List<Room> rooms = house.getRooms();
        for (Room room : rooms) {
            for (Window window : room.getWindows()) {
                if (window.getId() == id) {
                    window.setBlocked(true);
                    break;
                }
            }
        }
    }

    public Parameters getParameters() {
        return params;
    }

    public House getHouse() {
        return house;
    }

    @Override
    public void attachObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void detachObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Observable observable) {
        observers.forEach(observer -> {
            observer.update(observable);
        });
    }
}
