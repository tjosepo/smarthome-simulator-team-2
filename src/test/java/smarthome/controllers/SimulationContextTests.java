package smarthome.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import smarthome.models.User;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Test class used to test the SimulationContext class to make sure it works as intended
 * */
public class SimulationContextTests {
    private static SmartHomeSimulator smartHomeSimulator;

    @BeforeAll
    static void SmartHomeSimulator() {
        smartHomeSimulator = new SmartHomeSimulator();
    }

    @Test
    public void POST_MoveUser_Test() throws UnirestException, IOException {
        // Creating users
        Unirest.post("http://localhost:7000/api/add-user")
                .field("name", "Jack")
                .field("role", "Parent")
                .asString();
        Unirest.post("http://localhost:7000/api/add-user")
                .field("name", "Jill")
                .field("role", "Parent")
                .asString();

        // Creating rooms
        String json = "{\"rooms\":["+
                "{\"name\": \"Kitchen\",\"x\": 1,\"y\": 0,\"doors\": 1,\"windows\": 2,\"lights\": 1},"+
                "{\"name\": \"Dining\", \"x\": 1,\"y\": 1,\"doors\": 1,\"windows\": 2,\"lights\": 1}"+
                "]}";
        Unirest.post("http://localhost:7000/api/set-house-layout")
                .body(json)
                .asString();

        // Moving user
        HttpResponse<String> response = Unirest.post("http://localhost:7000/api/move-users")
                .field("locationUser0", "0")
                .field("locationUser1", "1")
                .asString();

        assertEquals(200, response.getCode(),"Status code must be 200");

        ArrayList<User> users = smartHomeSimulator.getParameters().getUsers();

        assertEquals("Kitchen", users.get(0).getLocation().getName());
        assertEquals("Dining", users.get(1).getLocation().getName());
        //JavalinJson.fromJson('hello', Class)
    }

}
