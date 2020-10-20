package smarthome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    }
}
