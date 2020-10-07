package smarthome;

/**
 * The type Door.
 */
public class Door {
    /**
     * The Id.
     */
   private int id;
    /**
     * The Opened.
     */
    private boolean opened = false;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Door{" +
                "id=" + id +
                ", opened=" + opened +
                '}';
    }
}
