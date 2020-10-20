package smarthome.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Layout file.
 */
public class LayoutFile {
    /**
     * The Rooms.
     */
    public RoomLayout[] rooms;

    /**
     * The type Room layout.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RoomLayout {
        /**
         * The Name.
         */
        public String name;
        /**
         * The X.
         */
        public int x;
        /**
         * The Y.
         */
        public int y;
        /**
         * The Width.
         */
        public int width;
        /**
         * The Height.
         */
        public int height;
        /**
         * The Windows.
         */
        public int windows;
        /**
         * The Doors.
         */
        public int doors;
        /**
         * The Lights.
         */
        public int lights;

        /**
         * Instantiates a new Room layout.
         *
         * @param name    the name
         * @param x       the x
         * @param y       the y
         * @param width   the width
         * @param height  the height
         * @param windows the windows
         * @param doors   the doors
         * @param lights  the lights
         */
        @JsonCreator
        public RoomLayout(
                @JsonProperty("name") String name,
                @JsonProperty("x") int x,
                @JsonProperty("y") int y,
                @JsonProperty("width") int width,
                @JsonProperty("height") int height,
                @JsonProperty("windows") int windows,
                @JsonProperty("doors") int doors,
                @JsonProperty("lights") int lights) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.windows = windows;
            this.doors = doors;
            this.lights = lights;
        }
    }
}
