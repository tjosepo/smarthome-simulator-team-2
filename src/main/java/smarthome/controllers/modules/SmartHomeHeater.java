package smarthome.controllers.modules;

import io.javalin.Javalin;
import io.javalin.http.Context;
import smarthome.controllers.Console;
import smarthome.controllers.House;
import smarthome.controllers.Parameters;
import smarthome.controllers.SmartHomeSimulator;
import smarthome.interfaces.Module;
import smarthome.interfaces.Observer;
import smarthome.interfaces.Observable;
import smarthome.models.Room;
import smarthome.models.User;

import java.util.*;

public class SmartHomeHeater implements Module, Observer {
    private Map<String, HeatingZone> zones = new HashMap();
    private House house;
    private Parameters params;
    private Map<String, Float> roomTemperature = new HashMap();
    private float summerTemperature = 21f;
    private float winterTemperature;
    private boolean awayMode = false;
    private boolean isHavcOn = true;

    public void loop(int delta) {
        for (int i = 0; i < delta; i++) {
            zones.values().forEach(zone -> updateZone(zone));
            roomTemperature.forEach((id, temp) -> updateRoom(id, temp));
        }
    }

    private void updateRoom(String id, float desiredTemp) {
        Room room = house.getRoom(Integer.parseInt(id));
        room.overridden = true;
        float outsideTemperature = params.getOutsideTemperature();
        if (room.temperature > desiredTemp + 0.25) {
            if (room.temperature > outsideTemperature && !awayMode) {
                room.getWindows().forEach(window -> window.setOpened(true));
            } else {
                room.getWindows().forEach(window -> window.setOpened(false));
            }
            room.isAc = true;
            room.isHeating = false;
            room.temperature -= 0.1;
        } else if (room.temperature < desiredTemp - 0.25) {
            if (room.temperature < outsideTemperature && !awayMode) {
                room.getWindows().forEach(window -> window.setOpened(true));
            } else {
                room.getWindows().forEach(window -> window.setOpened(false));
            }
            room.isAc = false;
            room.isHeating = true;
            room.temperature += 0.1;
        } else {
            room.getWindows().forEach(window -> window.setOpened(false));
        }

        if (room.temperature <= 0) {
            Console.print("[WARNING] Temperature is unusually low. Potential pipe burst.");
        }
    }

    private void updateZone(HeatingZone zone) {
        float outsideTemperature = params.getOutsideTemperature();
        if (!isHavcOn) {
            if (zone.temperature > outsideTemperature)
                zone.temperature -= 0.05;
            else if (zone.temperature < outsideTemperature)
                zone.temperature += 0.05;
            return;
        }

        if ((!awayMode && zone.temperature > zone.settings.targetTemperature + 0.25) || (awayMode && zone.temperature > summerTemperature + 0.25)) {
            // Open / Close windows
            if (zone.temperature > outsideTemperature && !awayMode) {
                zone.roomsIds.forEach(id -> {
                    Room room = house.getRoom(id);
                    room.getWindows().forEach(window -> window.setOpened(true));
                });
            } else {
                zone.roomsIds.forEach(id -> {
                    Room room = house.getRoom(id);
                    room.getWindows().forEach(window -> window.setOpened(false));
                });
            }
            // Adjust temperature
            zone.temperature -= 0.1;
        } else if ((!awayMode && zone.temperature < zone.settings.targetTemperature - 0.25) || (awayMode && zone.temperature < summerTemperature - 0.25)) {
            // Open / Close windows
            if (zone.temperature < outsideTemperature && !awayMode) {
                zone.roomsIds.forEach(id -> {
                    Room room = house.getRoom(id);
                    room.getWindows().forEach(window -> window.setOpened(true));
                });
            } else {
                zone.roomsIds.forEach(id -> {
                    Room room = house.getRoom(id);
                    room.getWindows().forEach(window -> window.setOpened(false));
                });
            }
            // Adjust temperature
            zone.temperature += 0.1;
        } else {
            zone.roomsIds.forEach(id -> {
                Room room = house.getRoom(id);
                room.getWindows().forEach(window -> window.setOpened(false));
            });
        }

        List<Room> rooms = house.getRooms();
        rooms.forEach(room -> {
            if (zone.roomsIds.contains(room.getId()) && !roomTemperature.containsKey(room.getId())) {
                room.overridden = false;
                if (room.temperature > zone.temperature) {
                    room.isAc = true;
                    room.isHeating = false;
                }
                if (room.temperature < zone.temperature) {
                    room.isHeating = true;
                    room.isAc = false;
                }
                room.temperature = zone.temperature;
            }
        });

        if (zone.temperature <= 0) {
            Console.print("[WARNING] Temperature is unusually low. Potential pipe burst.");
        }
    }

    @Override
    public void onLoad(Javalin app, SmartHomeSimulator shs) {
        shs.attachObserver(this);
        SmartHomeSecurity shp = (SmartHomeSecurity) shs.getModule(SmartHomeSecurity.class);
        shp.attachObserver(this);
        house = shs.getHouse();
        params = shs.getParameters();

        app.post("/api/set-temperature", ctx -> setTemperature(ctx));
        app.post("/api/set-zone", ctx -> setZone(ctx));
        app.post("/api/set-room-to-zone", ctx -> setRoomToZone(ctx));
    }

    private void setTemperature(Context ctx) {
        String type = ctx.formParam("option");
        int userId = Integer.parseInt(ctx.formParam("loggedAs"));
        User user = params.getUser(userId);
        ctx.json(zones.values());

        // Permissions
        if (user == null) {
            Console.print("[ERROR] User must be logged in to set temperature.");
            return;
        }

        String userRole = user.getRole();

        if (!userRole.equals("Parent") && !userRole.equals("Guest")) {
            Console.print("[ERROR] " + userRole + " is not allowed to set temperature.");
            return;
        }

        if (userRole.equals("Guest")) {
            if (!type.equals("roomTemperature")) {
                Console.print("[ERROR] " + userRole + " is only allowed to set room temperature.");
                return;
            }
            Room location = user.getLocation();
            if (location == null || Integer.parseInt(ctx.formParam("roomId")) != location.getId()) {
                Console.print("[ERROR] " + userRole + " is only allowed to set room temperature if inside the room.");
                return;
            }
        }

        float temperature = Float.parseFloat(ctx.formParam("temperature"));
        switch (type) {
            case "summerTemperature":
                summerTemperature = temperature;
                Console.print("Set summer temperature to " + temperature + " degrees.");
                break;
            case "winterTemperature":
                winterTemperature = temperature;
                break;
            case "zoneTemperature":
                String zoneId = ctx.formParam("zoneId");
                HeatingZone zone = zones.get(zoneId);
                zone.settings.targetTemperature = temperature;
                Console.print("Set " + zoneId + " temperature to " + temperature + " degrees.");
                zone.setTemperature(temperature);
                break;
            case "roomTemperature":
                String roomId = ctx.formParam("roomId");
                roomTemperature.put(roomId, temperature);
                Console.print("Set room temperature to " + temperature + " degrees.");
                break;
            case "removeRoomTemperature":
                String roomIv = ctx.formParam("roomId");
                roomTemperature.remove(roomIv);
                break;
            default:
                break;
        }

        ctx.json(zones.values());
    }

    private void setZone(Context ctx) {
        int userId = Integer.parseInt(ctx.formParam("loggedAs"));
        User user = params.getUser(userId);
        ctx.json(zones.values());

        // Permissions
        if (user == null) {
            Console.print("[ERROR] User must be logged in to edit zones.");
            return;
        }

        String userRole = user.getRole();

        if (!userRole.equals("Parent") && !userRole.equals("Guest")) {
            Console.print("[ERROR] " + userRole + " is not allowed to edit zones.");
            return;
        }
        String option = ctx.formParam("option"); // add / remove
        String zoneId = ctx.formParam("zoneId");

        switch (option) {
            case "add":
                zones.putIfAbsent(zoneId, new HeatingZone(zoneId));
                break;
            case "remove":
                zones.remove(zoneId);
                break;
        }

        ctx.json(zones.values());
    }

    private void setRoomToZone(Context ctx) {
        String option = ctx.formParam("option"); // add / remove
        String zoneId = ctx.formParam("zoneId");
        int roomId = Integer.parseInt(ctx.formParam("roomId"));

        switch (option) {
            case "add":
                zones.values().forEach(zone -> zone.removeRoom(roomId));
                zones.putIfAbsent(zoneId, new HeatingZone(zoneId));
                zones.get(zoneId).addRoom(roomId);
                Console.print("Added room " + roomId + " to " + zoneId + ".");
                break;
            case "remove":
                zones.values().forEach(zone -> zone.removeRoom(roomId));
                Console.print("Removed room from " + zoneId + ".");
                break;
        }

        ctx.json(zones.values());
    }

    @Override
    public void update(Observable o) {
        if (o instanceof SmartHomeSimulator) {
            updateSmartHomeSimulator((SmartHomeSimulator) o);
        } else if (o instanceof SmartHomeSecurity) {
            updateSmartHomeSecurity((SmartHomeSecurity) o);
        }
    }

    private void updateSmartHomeSimulator(SmartHomeSimulator shs) {
    }

    private void updateSmartHomeSecurity(SmartHomeSecurity shp) {
        this.awayMode = shp.getAwayMode();
    }
}

class HeatingZone {
    public String id;
    public TemperatureSettings settings = new TemperatureSettings();
    public List<Integer> roomsIds = new ArrayList();
    public float temperature = 0;

    public HeatingZone(String id) {
        this.id = id;
    }

    class TemperatureSettings {
        public float targetTemperature = 21.0f;
        public float desiredTemperatureDay;
        public float desiredTemperatureNight;
        public float desiredTemperatureEvening;
    }

    public void removeRoom(int id) {
        roomsIds.remove((Integer) id);
    }

    public void addRoom(int id) {
        roomsIds.add(id);
    }

    public boolean contains(int id) {
        return roomsIds.contains(id);
    }

    public void setTemperature(float temperature) {
        settings.targetTemperature = temperature;
    }

}