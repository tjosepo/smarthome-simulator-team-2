package smarthome.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

class SimulationParametersTest {
    private SimulationParameters testParam;


    @BeforeEach
    void BeforeEachSimulationPerametersTest() {
        testParam = new SimulationParameters();
    }

    @Test
    void addUser() {
        testParam.addUser("Rick", "Parent");
        assertEquals(1, testParam.getUsers().size());
        testParam.addUser("Peter", "Child");
        assertEquals(2, testParam.getUsers().size());
    }

    @Test
    void removeUser() {
        testParam.addUser("Rick", "Parent");
        testParam.addUser("Peter", "Child");
        assertEquals(2, testParam.getUsers().size());
        testParam.removeUser(testParam.getUsers().get(0).getId());
        assertEquals(1, testParam.getUsers().size());
        assertEquals("Peter", testParam.getUsers().get(0).getName());
    }

    @Test
    void setDate() {
        testParam.setDate(new Date(1302979200));
        Date expected = new Date(1302979200);
        assertEquals(expected, testParam.getDate());
    }

    @Test
    void setLocation() {
        testParam.setLocation("TestLocation");
        assertEquals("TestLocation", testParam.getLocation());
        testParam.setLocation("NewLocation");
        assertEquals("NewLocation", testParam.getLocation());

    }

    @Test
    void logInAs() {
        testParam.addUser("Rick", "Parent");
        testParam.addUser("Peter", "Child");
        testParam.logInAs(testParam.getUsers().get(0).getId());
        assertEquals("Rick", testParam.getLoggedAs().getName());
    }
}