package smarthome.controllers;
import smarthome.models.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class of HouseLayout type
 */
public class HouseLayout {

    /**
     * Static class of RoomLayout type
     */
    private static class RoomLayout {
        private String name;
        private int windows;
        private int lights;
        private int doors;
    }

    private List<Room> rooms;
    private Room outside;

    /**
     * Class default constructor
     */
    public HouseLayout() {
        this.rooms = new ArrayList<Room>();
        this.outside = new Room("outside", null, null, null);
    }

    /**
     * Class constructor
     *
     * @param jsonLayout    The layout of the house in json formatted string
     */
    public HouseLayout(String jsonLayout) {
        this.outside = new Room("outside", null, null, null);

        readLayoutFile(jsonLayout);

    }

    /**
     * Reads a json representation of a house layout and models the house
     *
     * @param jsonLayout    The layout of the house in json formatted string
     */
    public void readLayoutFile(String jsonLayout) {
        rooms = new ArrayList<Room>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<RoomLayout> layout = mapper.readValue(jsonLayout, new TypeReference<List<RoomLayout>>(){});

            for (RoomLayout roomLayout : layout) {
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

                rooms.add(new Room(roomLayout.name, windows, lights, doors));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
