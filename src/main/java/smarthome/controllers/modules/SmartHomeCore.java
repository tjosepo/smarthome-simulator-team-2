package smarthome.controllers.modules;

import io.javalin.Javalin;
import io.javalin.http.Context;
import smarthome.controllers.Console;
import smarthome.controllers.House;
import smarthome.controllers.Parameters;
import smarthome.controllers.SmartHomeSimulator;
import smarthome.exceptions.LoggingException;
import smarthome.interfaces.Module;
import smarthome.interfaces.Observable;
import smarthome.interfaces.Observer;
import smarthome.models.*;

import java.util.List;

public class SmartHomeCore implements Module, Observer {
    private boolean autoMode = false;

    @Override
    public void onLoad(Javalin app, SmartHomeSimulator shs) {
        shs.attachObserver(this);

        app.post("/api/toggle-light", ctx -> {
            toggleLight(ctx, shs);
            ctx.json(shs.getHouse().getRooms());
        });

        app.post("/api/toggle-window", ctx -> {
            toggleWindow(ctx, shs);
            ctx.json(shs.getHouse().getRooms());
        });

        app.post("/api/toggle-door", ctx -> {
            toggleDoor(ctx, shs);
            ctx.json(shs.getHouse().getRooms());
        });

        app.post("/api/toggle-auto-mode", ctx -> {
            autoMode = Boolean.parseBoolean(ctx.formParam("autoMode"));
            Console.print("Auto mode set to " + this.autoMode + ".");
        });
    }

    public void print(String message) {
        try {
            Console.print(message);
        } catch (LoggingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Observable o) {
        SmartHomeSimulator shs = (SmartHomeSimulator) o;

        if (autoMode) {
            autoToggleLights(shs);
            try {
                Console.print("Lights toggled automatically.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void toggleLight(Context ctx, SmartHomeSimulator shs) {
        int id = Integer.parseInt(ctx.formParam("id"));
        boolean isOn = Boolean.parseBoolean(ctx.formParam("isOn"));

        Parameters params = shs.getParameters();
        User user = params.getLoggedAs();
        if (user == null) {
            try {
                Console.print("[ERROR] User must be logged in to toggle light.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        String userRole = user.getRole();
        Room userLocation = user.getLocation();

        House house = shs.getHouse();
        List<Room> rooms = house.getRooms();
        Room lightLocation = null;
        Light lightToToggle = null;

        for (Room room : rooms) {
            List<Light> lights = room.getLights();
            for (Light light : lights) {
                if (light.getId() == id) {
                    lightToToggle = light;
                    lightLocation = room;
                }
            }
        }

        if (lightToToggle == null) {
            try {
                Console.print("[ERROR] Could not find light to toggle.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        // Permissions
        if (userRole.equals("Stranger")) {
            try {
                Console.print("[ERROR] Stranger is not allowed to toggle lights.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        if (!userRole.equals("Parent") && userLocation == null) {
            try {
                Console.print("[ERROR] " + userRole + " is not allowed to toggle light when outside of house.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        if (!userRole.equals("Parent") && userLocation != lightLocation) {
            try {
                Console.print("[ERROR] " + userRole + " is not allowed to toggle " + lightLocation.getName() + " light while in " + userLocation.getName());
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        lightToToggle.setOn(isOn);
        try {
            Console.print("Toggled light " + (isOn ? "on" : "off") + ".");
        } catch (LoggingException e) {
            System.out.println(e.getMessage());
        }
    }

    private void toggleWindow(Context ctx, SmartHomeSimulator shs) {
        int id = Integer.parseInt(ctx.formParam("id"));
        boolean isOpened = Boolean.parseBoolean(ctx.formParam("isOpened"));

        Parameters params = shs.getParameters();
        User user = params.getLoggedAs();
        if (user == null) {
            try {
                Console.print("[ERROR] User must be logged in to toggle window.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }
        String userRole = user.getRole();
        Room userLocation = user.getLocation();

        House house = shs.getHouse();
        List<Room> rooms = house.getRooms();
        Room windowLocation = null;
        Window windowToToggle = null;

        for (Room room : rooms) {
            List<Window> windows = room.getWindows();
            for (Window window : windows) {
                if (window.getId() == id) {
                    windowToToggle = window;
                    windowLocation = room;
                }
            }
        }

        if (windowToToggle == null) {
            try {
                Console.print("[ERROR] Could not find window to toggle.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        // Permissions
        if (userRole.equals("Stranger")) {
            try {
                Console.print("[ERROR] Stranger is not allowed to toggle window.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        if (!userRole.equals("Parent") && userLocation == null) {
            try {
                Console.print("[ERROR] " + userRole + " is not allowed to toggle window when outside of house.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        if (!userRole.equals("Parent") && userLocation != windowLocation) {
            try {
                Console.print("[ERROR] " + userRole + " is not allowed to toggle " + windowLocation.getName() + " window while in " + userLocation.getName());
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        windowToToggle.setOpened(isOpened);
    }

    private void toggleDoor(Context ctx, SmartHomeSimulator shs) {
        int id = Integer.parseInt(ctx.formParam("id"));
        boolean isOpened = Boolean.parseBoolean(ctx.formParam("isOpened"));

        Parameters params = shs.getParameters();
        User user = params.getLoggedAs();
        if (user == null) {
            try {
                Console.print("[ERROR] User must be logged in to toggle door.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }
        String userRole = user.getRole();
        Room userLocation = user.getLocation();

        House house = shs.getHouse();
        List<Room> rooms = house.getRooms();
        Room doorLocation = null;
        Door doorToToggle = null;

        for (Room room : rooms) {
            List<Door> doors = room.getDoors();
            for (Door door : doors) {
                if (door.getId() == id) {
                    doorToToggle = door;
                    doorLocation = room;
                }
            }
        }

        if (doorToToggle == null) {
            try {
                Console.print("[ERROR] Could not find door to toggle.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        // Permissions
        if (!userRole.equals("Parent")) {
            try {
                Console.print("[ERROR] " + userRole + " is not allowed to toggle door.");
            } catch (LoggingException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        doorToToggle.setOpened(isOpened);
        try {
            Console.print((isOpened ? "Opened" : "Closed") + " door.");
        } catch (LoggingException e) {
            System.out.println(e.getMessage());
        }
    }

    private void autoToggleLights(SmartHomeSimulator shs) {
        Parameters params = shs.getParameters();
        House house = shs.getHouse();

        for (Room room : house.getRooms()) {
            for (Light light : room.getLights()) {
                light.setOn(false);
            }
        }

        for (User user : params.getUsers()) {
            Room userLocation = user.getLocation();
            if (userLocation == null) {
                return;
            }

            for (Light light : userLocation.getLights()) {
                light.setOn(true);
            }
        }
    }

    public void loop(int delta) {
        return;
    }
}
