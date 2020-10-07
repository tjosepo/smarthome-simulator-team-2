package smarthome;

/**
 * The type Window.
 */
public class Window {
    /**
     * The Id.
     */
    private int id;
    /**
     * The Opened.
     */
   private boolean opened;
    /**
     * The Blocked.
     */
   private boolean blocked;

    /**
     * Instantiates a new Window.
     *
     * @param id      the id
     * @param opened  the opened
     * @param blocked the blocked
     */
    public Window(int id, boolean opened, boolean blocked) {
        this.id = id;
        this.opened = opened;
        this.blocked = blocked;
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
     * Is opened boolean.
     *
     * @return the boolean
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     * Is blocked boolean.
     *
     * @return the boolean
     */
    public boolean isBlocked() {
        return blocked;
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
     * Sets opened.
     *
     * @param opened the opened
     */
    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    /**
     * Sets blocked.
     *
     * @param blocked the blocked
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "Window{" +
                "id=" + id +
                ", opened=" + opened +
                ", blocked=" + blocked +
                '}';
    }
}
