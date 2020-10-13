package smarthome.models;

/**
 * Class of type User.
 */
public class User{
    /**
     * Creating a private int called id.
     */
    private static int id = 0;
    /**
     * Creating a private String called name.
     */
    private String name;
    /**
     * Creating a private string called role.
     */
    private String role;
    /**
     * Creating a Room called location.
     */
    private Room location;

    /**
     * Instantiates a new User.
     *
     * @param name     the String name
     * @param role     the user's role
     */

    public User(String name,String role) {
        id++;
        this.name = name;
        this.role = role;
    }

    /**
     * Getter method that will return id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method that will return name.
     *
     * @return the name
     */
    public String getName() {
        return name;
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
     * Getter method that will get the location.
     *
     * @return the location
     */
    public Room getLocation() {
        return location;
    }

    /**
     * Setter method that will set the location.
     *
     * @param location the location
     */
    public void setLocation(Room location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location=" + location +
                '}';
    }
}