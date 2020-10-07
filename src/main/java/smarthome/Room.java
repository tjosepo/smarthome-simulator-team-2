package smarthome;

import java.util.Arrays;

/**
 * The type Room.
 */
public class Room {
    /**
     * The Id.
     */
    private int id;
    /**
     * The Name.
     */
    private String name;
    /**
     * The Windows.
     */
   private Window[] windows;
    /**
     * The Lights.
     */
   private Light[] lights;
    /**
     * The Doors.
     */
   private Door[] doors;

    /**
     * Instantiates a new Room.
     *
     * @param id      the id
     * @param name    the name
     * @param windows the windows
     * @param lights  the lights
     * @param doors   the doors
     */
    public Room(int id, String name, Window[] windows, Light[] lights, Door[] doors) {
        this.id = id;
        this.name = name;
        this.windows = windows;
        this.lights = lights;
        this.doors = doors;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get windows window [ ].
     *
     * @return the window [ ]
     */
    public Window[] getWindows() {
        return windows;
    }

    /**
     * Get lights light [ ].
     *
     * @return the light [ ]
     */
    public Light[] getLights() {
        return lights;
    }

    /**
     * Get doors door [ ].
     *
     * @return the door [ ]
     */
    public Door[] getDoors() {
        return doors;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets windows.
     *
     * @param windows the windows
     */
    public void setWindows(Window[] windows) {
        this.windows = windows;
    }

    /**
     * Sets lights.
     *
     * @param lights the lights
     */
    public void setLights(Light[] lights) {
        this.lights = lights;
    }

    /**
     * Sets doors.
     *
     * @param doors the doors
     */
    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", windows=" + Arrays.toString(windows) +
                ", lights=" + Arrays.toString(lights) +
                ", doors=" + Arrays.toString(doors) +
                '}';
    }
}
