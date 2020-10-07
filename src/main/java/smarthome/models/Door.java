package smarthome.models;

/**
 * Class of type Door.
 */
public class Door {
    /**
     * Creating a private in called id.
     */
   private int id;
    /**
     * Creating a private boolean called opened.
     */
    private boolean opened = false;

    /**
     * Instantiates a new Door.
     *
     * @param id the int id
     * @param opened the boolean opened
     */
    public Door(int id,  boolean opened)
    {
        this.id = id;
        this.opened= opened;

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
