package smarthome.controllers;

import io.javalin.Javalin;
import smarthome.models.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HouseLayout {
    private ArrayList<Room> rooms;

    public HouseLayout(Javalin app) {
        app.post("/api/set-house-layout", ctx -> {
            LayoutFile layoutFile = ctx.bodyAsClass(LayoutFile.class);
            this.rooms = readLayoutFile(layoutFile);
            ctx.json(rooms);
        });
    }

    public ArrayList<Room> readLayoutFile(LayoutFile layoutFile) {
        ArrayList<Room> rooms = new ArrayList<>();
        for (LayoutFile.RoomLayout roomLayout : layoutFile.rooms) {
            Window windows[] = new Window[roomLayout.windows];
            Light lights[] = new Light[roomLayout.lights];
            Door doors[] = new Door[roomLayout.doors];

            for (int i = 0; i < roomLayout.windows; i++) {
                windows[i] = new Window();
            }
            for (int i = 0; i < roomLayout.lights; i++) {
                lights[i] = new Light();
            }
            for (int i = 0; i < roomLayout.doors; i++) {
                doors[i] = new Door();
            }

            rooms.add(new Room(roomLayout.name, windows, lights, doors, roomLayout.x, roomLayout.y, roomLayout.width, roomLayout.height));
        }
        return rooms;
    }

    public Room getRoom(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
