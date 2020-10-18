package smarthome.models;

/**
 * Class of type Light.
 */
public class Light {
    /**
     * Creating a private int called id.
     */
   private int id = 0;
    /**
     * Creating a private boolean called on.
     */
   private boolean on;

    /**
     * Default constructor that will increment the static variable id by 1 every time a light is created, and initializing boolean on to false
     */
    public Light()
    {
        id++;
        this.on = false;
    }

   /**
     * Instantiates a new light with the following parameters.
     *
     *
     * @param on      the boolean opened
     *
     */

    public Light(boolean on) {
        id++;
        this.on = on;
    }

    /**
     *  Getter method that returns id.
     *
     * @return the id
     */

    public int getId() {
        return id;
    }

     /**
     * Getter method that returns value of boolean on.
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
