package smarthome.models;

import java.util.Arrays;

/**
 * Class of type Room.
 */
public class Room {
    /**
     *  Creating a private int called id.
     */
    private int id;
    /**
     *  Creating a private static int called numRooms.
     */
    private static int numRooms = 0;
    /**
     * Creating private string called name.
     */
    private String name;
    /**
     * Creating private array of window objects called windows.
     */
   private Window[] windows;
    /**
     * Creating private array of light objects called lights.
     */
   private Light[] lights;
    /**
     * Creating a private array of door objects called doors.
     */
   private Door[] doors;

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
     * Instantiates a new Room with the following parameters.
     *
     * @param name    the name
     * @param windows the windows
     * @param lights  the lights
     * @param doors   the doors
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     */
    public Room(String name, Window[] windows, Light[] lights, Door[] doors, int x, int y, int width, int height) {
        id = numRooms++;
        this.name = name;
        this.windows = windows;
        this.lights = lights;
        this.doors = doors;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter method that returns id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method that returns name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method that returns the array windows .
     *
     * @return the window [ ]
     */
    public Window[] getWindows() {
        return windows;
    }

    /**
     * Getter method that returns the array lights.
     *
     * @return the light [ ]
     */
    public Light[] getLights() {
        return lights;
    }

    /**
     * Getter method that returns the array doors.
     *
     * @return doors door [ ]
     */
    public Door[] getDoors() {
        return doors;
    }


    /**
     * Setter method that will set the name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter method that will set the array windows.
     *
     * @param windows the windows
     */
    public void setWindows(Window[] windows) {
        this.windows = windows;
    }

    /**
     * Setter method that will set the array lights.
     *
     * @param lights the lights
     */
    public void setLights(Light[] lights) {
        this.lights = lights;
    }

    /**
     * Setter method that will set the array doors.
     *
     * @param doors the doors
     */
    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    /**
     * To String method that will display the features of the room
     * @return String
     */
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
