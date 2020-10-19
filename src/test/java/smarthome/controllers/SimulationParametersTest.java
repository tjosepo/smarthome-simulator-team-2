package smarthome.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationParametersTest {

    @Test
    void addUser() {
        SimulationParameters testParam = new SimulationParameters(new Date(), "TestLocation");
        testParam.addUser("Rick", "Parent");
        assertEquals(1, testParam.getUsers().size());
        testParam.addUser("Peter", "Child");
        assertEquals(2, testParam.getUsers().size());
    }

    @Test
    void removeUser() {
        SimulationParameters testParam = new SimulationParameters(new Date(), "TestLocation");
        testParam.addUser("Rick", "Parent");
        testParam.addUser("Peter", "Child");
        assertEquals(2, testParam.getUsers().size());
        testParam.removeUser(testParam.getUsers().get(0).getId());
        assertEquals(1, testParam.getUsers().size());
        assertEquals("Peter", testParam.getUsers().get(0).getName());
    }

    @Test
    void setDate() {
        SimulationParameters testParam = new SimulationParameters(new Date(), "TestLocation");
        testParam.setDate(new Date(1302979200));
        Date expected = new Date(1302979200);
        assertEquals(expected, testParam.getDate());
    }

    @Test
    void setLocation() {
        SimulationParameters testParam = new SimulationParameters(new Date(), "TestLocation");
        assertEquals("TestLocation", testParam.getLocation());
        testParam.setLocation("NewLocation");
        assertEquals("NewLocation", testParam.getLocation());

    }

    @Test
    void logInAs() {
        SimulationParameters testParam = new SimulationParameters(new Date(), "TestLocation");
        testParam.addUser("Rick", "Parent");
        testParam.addUser("Peter", "Child");
        testParam.logInAs(testParam.getUsers().get(0).getId());
        assertEquals("Rick", testParam.getLoggedAs().getName());
    }
}