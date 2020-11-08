package smarthome.controllers.modules;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.json.JavalinJson;
import smarthome.controllers.Console;
import smarthome.controllers.House;
import smarthome.controllers.Parameters;
import smarthome.controllers.SmartHomeSimulator;
import smarthome.interfaces.Module;
import smarthome.interfaces.Observable;
import smarthome.interfaces.Observer;
import smarthome.models.*;

import java.util.LinkedList;
import java.util.List;

public class SmartHomeSecurity implements Module, Observer {
    private boolean awayMode = false;
    private List<Integer> lightsToKeepOn = new LinkedList();

    @Override
    public void onLoad(Javalin app, SmartHomeSimulator shs) {
        shs.attachObserver(this);
        app.post("/api/away-mode", ctx -> {
            setAwayMode(ctx, shs);
            if (awayMode) lockEverything(shs);
            ctx.result("{\"house\":" + JavalinJson.toJson(shs.getHouse()) + ",\"awayMode\":" + awayMode + "}");
        });
        app.post("/api/keep-light-on", ctx -> {
            keepLightOn(ctx, shs);
            if (awayMode) lockEverything(shs);
            ctx.json(shs.getHouse().getRooms());
        });
    }

    public void update(Observable o) {
        if (!awayMode) {
            return;
        }
        SmartHomeSimulator shs = (SmartHomeSimulator) o;
        Parameters params = shs.getParameters();
        List<User> users = params.getUsers();

        boolean houseIsEmpty = true;
        boolean strangerInTheHouse = false;
        for (User user : users) {
            if (user.getRole().equals("Stranger") && user.getLocation() != null) {
                strangerInTheHouse = true;
                continue;
            }
            if (user.getLocation() != null) {
                houseIsEmpty = false;
                break;
            }
        }

        if (houseIsEmpty) {
            Console.print("House is empty.");
        }

        if (houseIsEmpty && strangerInTheHouse) {
            Console.print("[ALERT] Stranger in the house.");
            Module m = shs.getModule(SmartHomeCore.class);
            if (m != null) {
                SmartHomeCore shc = (SmartHomeCore) m;
            }
        }
    }

    private void setAwayMode(Context ctx, SmartHomeSimulator shs) {
        Parameters params = shs.getParameters();
        User loggedAs = params.getLoggedAs();

        // Permissions
        if (loggedAs == null) {
            Console.print("[ERROR] User must be logged in to toggle away mode.");
            return;
        }

        String userRole = loggedAs.getRole();

        if (!userRole.equals("Parent") && !userRole.equals("Child")) {
            Console.print("[ERROR] " + userRole + " is not allowed to toggle away mode.");
            return;
        }

        for (User user : params.getUsers()) {
            if (user.getLocation() != null) {
                Console.print("[ERROR] Cannot set away mode while someone is in the house.");
                return;
            }
        }

        awayMode = Boolean.parseBoolean(ctx.formParam("awayMode"));
        Console.print("Away mode set to " + this.awayMode + ".");
    }

    private void keepLightOn(Context ctx, SmartHomeSimulator shs) {
        int id = Integer.parseInt(ctx.formParam("id"));
        boolean keepOn = Boolean.parseBoolean(ctx.formParam("keepOn"));

        if (keepOn && !lightsToKeepOn.contains(id)) {
            lightsToKeepOn.add(id);
        }
        if (!keepOn && lightsToKeepOn.contains(id)) {
            lightsToKeepOn.remove(Integer.valueOf(id));
        }
    }

    private void lockEverything(SmartHomeSimulator shs) {
        House house = shs.getHouse();
        for (Room room : house.getRooms()) {
            for (Window window : room.getWindows()) {
                window.setOpened(false);
            }
            for (Light light : room.getLights()) {
                if (lightsToKeepOn.contains(light.getId())) {
                    light.setOn(true);
                } else {
                    light.setOn(false);
                }
            }
            for (Door door : room.getDoors()) {
                door.setOpened(false);
            }
        }
        Console.print("Locked everything.");
    }
}
