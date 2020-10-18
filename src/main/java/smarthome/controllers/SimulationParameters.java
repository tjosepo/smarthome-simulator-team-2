package smarthome.controllers;
import smarthome.models.*;
import java.util.Date;
import java.util.ArrayList;

/**
 * Class of type SimulationParameters
 */
public class SimulationParameters {
    private ArrayList<User> users;
    private Date date;
    private String location;
    private User loggedAs;

    /**
     * Class constructor
     *
     * @param date      Simulation date
     * @param location  Simulation location
     */
    public SimulationParameters(Date date, String location) {
        this.date = date;
        this.location = location;
    }

    /**
     * Adds a users to the arraylist of users if user has a valid role
     *
     * @param name  Name of the new user to add
     * @param role  Role of the new user to add
     */
    public void addUser(String name, String role) {
        if(role.equals("Parent") || role.equals("Child") || role.equals("Guest") || role.equals("Stranger"))
            users.add(new User(name, role));
    }

    /**
     * Removes a user
     *
     * @param id    The id of the user to be removed
     */
    public void removeUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(i);
            }
        }
    }

    /**
     * Changes the date of the simulation
     *
     * @param date  The new Date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Changes the location of the simulation
     *
     * @param location  The new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Logs in as a user
     *
     * @param id The ID of the user to be logged in as
     */
    public void logInAs(int id) {
        for(int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id)
                loggedAs = users.get(i);
        }
    }

}
