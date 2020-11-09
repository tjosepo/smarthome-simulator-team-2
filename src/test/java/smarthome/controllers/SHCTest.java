package smarthome.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import smarthome.controllers.modules.SmartHomeCore;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Test class used to test the SimulationContext class to make sure it works as intended
 * */
public class SHCTest {
    private static SmartHomeSimulator shs;

    @BeforeAll
    static void SmartHomeSimulator() throws UnirestException {
        shs = new SmartHomeSimulator();
        shs.loadModule(new SmartHomeCore());

        // Creating users
        Unirest.post("http://localhost:7000/api/add-user")
                .field("name", "Jack")
                .field("role", "Parent")
                .asString();

        // Creating room
        String json = "{\"rooms\":["+
                "{\"name\": \"Kitchen\",\"x\": 1,\"y\": 0,\"doors\": 1,\"windows\": 2,\"lights\": 1}]}";
        Unirest.post("http://localhost:7000/api/set-house-layout")
                .body(json)
                .asString();

        // Logging in
        Unirest.post("http://localhost:7000/api/log-in-as")
                .field("id", "0")
                .asString();
    }

    @Test
    public void POST_ToggleLight_Test() throws UnirestException, IOException {
        // Toggling light
        Unirest.post("http://localhost:7000/api/toggle-light")
                .field("id", "1")
                .field("isOn", "true")
                .asString();

        boolean lightIsOn = shs.getHouse().getRoom(0).getLights().get(0).isOn();
        assertTrue(lightIsOn);
    }

    @Test
    public void POST_ToggleWindow_Test() throws  UnirestException, IOException {

        // Toggling window
        Unirest.post("http://localhost:7000/api/toggle-window")
                .field("id", "0")
                .field("isOpened", "true")
                .asString();

        boolean windowIsOpen = shs.getHouse().getRoom(0).getWindows().get(0).isOpened();
        assertTrue(windowIsOpen);
    }

    @Test
    public void POST_ToggleDoor_Test() throws  UnirestException, IOException {

        // Toggling window
        Unirest.post("http://localhost:7000/api/toggle-door")
                .field("id", "1")
                .field("isOpened", "true")
                .asString();

        boolean doorIsOpen = shs.getHouse().getRoom(0).getDoors().get(0).getOpened();
        assertTrue(doorIsOpen);
    }

//    @Test
//    public void POST_ToggleAutoMode_Test() throws UnirestException, IOException {
//        // Toggling auto mode
//        Unirest.post("http://localhost:7000/api/toggle-auto-mode")
//                .field("autoMode", "true")
//                .asString();
//
//    }

}
