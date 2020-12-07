package smarthome.controllers;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import io.javalin.websocket.WsConnectContext;
import smarthome.interfaces.Module;
import smarthome.interfaces.Observable;
import smarthome.interfaces.Observer;
import smarthome.models.Room;
import smarthome.models.User;
import smarthome.models.Window;

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

    private boolean isSimulating = false;
    private List<WsConnectContext> connections = new LinkedList<>();
    private int simulationSpeed = 1;

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
        params = new Parameters(app, this);

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

        app.post("/api/start-simulation", ctx -> {
            isSimulating = true;
            startSimulation();
        });
        app.post("/api/stop-simulation", ctx -> isSimulating = false);
        app.post("/api/set-simulation-speed", ctx -> {
            simulationSpeed = Integer.parseInt(ctx.formParam("simulationSpeed"));
            Console.print("Simulation speed set to " + simulationSpeed + ".");
        });
        app.ws("/websocket/simulation", ws -> {
            ws.onConnect(ctx -> {
                connections.add(ctx);
            });
            ws.onClose(ctx -> {
                connections.remove(ctx);
            });
        });
    }

    private void startSimulation() {
        new Thread(() -> {
            Console.print("Simulation start.");
            house.getRooms().forEach(room -> room.temperature = 0);
            while (isSimulating) {
                modules.forEach(module -> module.loop(simulationSpeed));
                String json = JavalinJson.toJson(house.getRooms());
                connections.forEach(connection -> connection.send(json));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Console.print("[ERROR] " + e.getMessage());
                }
            }
            Console.print("Simulation stop.");
        }).run();
    }

    public void loadModule(Module m) {
        m.onLoad(app, this);
        modules.add(m);
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
