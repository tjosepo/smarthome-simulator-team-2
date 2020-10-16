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
    private List<Room> rooms;
    private Room outside;

    /**
     * Static class of RoomLayout type
     */
    private static class RoomLayout {
        private String name;
        private int windows;
        private int lights;
        private int doors;
    }

    /**
     * Class of LayoutFile type
     */
    private class LayoutFile {
        private List<RoomLayout> layout;

        /**
         * Class constructor
         *
         * @param jsonLayout    The layout of the house in json formatted string
         */
        public LayoutFile(String jsonLayout) {

            try {
                ObjectMapper mapper = new ObjectMapper();
                layout = mapper.readValue(jsonLayout, new TypeReference<List<RoomLayout>>(){});
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * Class default constructor
     */
    public HouseLayout() {
        this.rooms = new ArrayList<Room>();
        this.outside = new Room(0, "outside", null, null, null);
    }

    /**
     * Class constructor
     *
     * @param jsonLayout    The layout of the house in json formatted string
     */
    public HouseLayout(String jsonLayout) {
        this.outside = new Room(0, "outside", null, null, null);
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<RoomLayout> layout = mapper.readValue(jsonLayout, new TypeReference<List<RoomLayout>>(){});
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readLayoutFile(String jsonLayout) {

    }


}
