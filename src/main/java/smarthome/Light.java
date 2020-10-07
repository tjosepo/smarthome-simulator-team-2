package smarthome;

/**
 * The type Light.
 */
public class Light {
    /**
     * The Id.
     */
   private int id;
    /**
     * The On.
     */
   private boolean on = false;

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

    /**
     * Is on boolean.
     *
     * @return the boolean
     */
    public boolean isOn() {
        return on;
    }

    /**
     * Sets on.
     *
     * @param on the on
     */
    public void setOn(boolean on) {
        this.on = on;
    }

    @Override
    public String toString() {
        return "Light{" +
                "id=" + id +
                ", on=" + on +
                '}';
    }
}
