package smarthome.controllers;

import io.javalin.Javalin;
import smarthome.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The type House layout.
 */
public class House {
    private List<Room> rooms;

    /**
     * Instantiates a new default empty house layout.
     */
    public House() {
    }

    /**
     * Instantiates a new House layout from a file.
     *
     * @param app the app
     */
    public House(Javalin app) {
        app.post("/api/set-house-layout", ctx -> {
            Console.print("House layout has been set.");
            LayoutFile layoutFile = ctx.bodyAsClass(LayoutFile.class);
            this.rooms = readLayoutFile(layoutFile);
            ctx.json(rooms);
        });
    }

    /**
     * Method that will read layout file and place the rooms in an array list.
     *
     * @param layoutFile the layout file
     * @return the array list
     */
    public List<Room> readLayoutFile(LayoutFile layoutFile) {
        List<Room> rooms = new ArrayList<>();
        for (LayoutFile.RoomLayout roomLayout : layoutFile.rooms) {
            List<Window> windows = new ArrayList(roomLayout.windows);
            List<Light> lights = new ArrayList(roomLayout.lights);
            List<Door> doors = new ArrayList(roomLayout.doors);

            for (int i = 0; i < roomLayout.windows; i++) {
                windows.add(new Window());
            }
            for (int i = 0; i < roomLayout.lights; i++) {
                lights.add(new Light());
            }
            for (int i = 0; i < roomLayout.doors; i++) {
                doors.add(new Door());
            }

            rooms.add(new Room(roomLayout.name, windows, lights, doors, roomLayout.x, roomLayout.y, roomLayout.width, roomLayout.height));
        }
        return rooms;
    }

    /**
     * Gets room from an id.
     *
     * @param id the id
     * @return the room
     */
    public Room getRoom(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }

    public Window getWindow(int id) {
        for (Room room : rooms) {
            for (Window window : room.getWindows()) {
                if (window.getId() == id) {
                    return window;
                }
            }
        }
        return null;
    }

    /**
     * Gets rooms.
     *
     * @return the rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }
}
