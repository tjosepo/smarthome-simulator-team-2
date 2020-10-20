package smarthome.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LayoutFile {
    public RoomLayout[] rooms;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RoomLayout {
        public String name;
        public int x;
        public int y;
        public int width;
        public int height;
        public int windows;
        public int doors;
        public int lights;

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
