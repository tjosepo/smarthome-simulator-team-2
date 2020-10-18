package smarthome.models;

/**
 * Class of type Door.
 */
public class Door {
    /**
     * Creating a private in called id.
     */
   private int id = 0;

    /**
     * Creating a private boolean called opened.
     */
    private boolean opened;

    /**
     * Default constructor that will increment the static variable id by 1 every time a door is created, and initializing boolean opened to false
     */
    public Door()
    {
        id++;
        this.opened = false;
    }

    /**
     * Instantiates a new Door.
     *
     * @param opened the boolean opened
     */
    public Door(boolean opened)
    {
        id++;
        this.opened = opened;

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
     * To String method that will display the features of the room
     * @return String
     */
    @Override
    public String toString() {
        return "Door{" +
                "id=" + id +
                ", opened=" + opened +
                '}';
    }
}
