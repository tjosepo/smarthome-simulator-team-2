package smarthome.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import smarthome.models.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class of type SimulationParameters
 */
public class Parameters {
    private ArrayList<User> users = new ArrayList<>();
    private Date date;
    private String location;
    private User loggedAs;

    /**
     * Accessor for users
     *
     * @return users users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Accessor for Date
     *
     * @return date date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Accessor for location
     *
     * @return location location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Accessor for loggedAs
     *
     * @return loggedAs logged as
     */
    public User getLoggedAs() {
        return loggedAs;
    }

    /**
     * Class constructor that will assign the value of the "name" field to the variable name, same thing for the "role" field
     *
     * @param app Javalin object
     */
    public Parameters(Javalin app) {
        app.post("/api/add-user", ctx -> {
            String name = ctx.formParam("name");
            String role = ctx.formParam("role");
            addUser(name, role);
            ctx.json(users);
            Console.print("User \"" + name + "\" has been added.");
        });

        app.post("/api/delete-user", ctx -> {
            int id = Integer.parseInt(ctx.formParam("id"));
            User user = removeUser(id);
            ctx.json(users);
            Console.print("User \"" + user.getName() + "\" has been removed.");
        });

        app.post("/api/log-in-as", ctx -> {
            logInAs(ctx);
            ctx.json(loggedAs);
        });
    }

    /**
     * Class constructor that doesn't expose an API.
     */
    public Parameters() {
    }


    /**
     * Adds a users to the arraylist of users if user has a valid role
     *
     * @param name Name of the new user to add
     * @param role Role of the new user to add
     */
    public void addUser(String name, String role) {
        if (role.equals("Parent") || role.equals("Child") || role.equals("Guest") || role.equals("Stranger"))
            users.add(new User(name, role));
    }

    /**
     * Removes a user
     *
     * @param id The id of the user to be removed
     */
    public User removeUser(int id) {
        User userToRemove = null;
        for (User user : users) {
            if (user.getId() == id) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove != null) {
            users.remove(userToRemove);
        }

        return userToRemove;
    }

    /**
     * Gets a user
     *
     * @param id The id of the user to be returned
     * @return the user
     */
    public User getUser(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * Changes the date of the simulation
     *
     * @param date The new Date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Changes the location of the simulation
     *
     * @param location The new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Logs in as a user
     */
    public void logInAs(Context ctx) {
        int id = Integer.parseInt(ctx.formParam("id"));
        if (id == -1) {
            Console.print("Logged off.");
        }
        User user = getUser(id);
        loggedAs = user;
        Console.print("Logged in as " + user.getName() + ".");
    }

}
