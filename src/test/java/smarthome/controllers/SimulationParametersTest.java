//package smarthome.controllers;
//
//import org.junit.jupiter.api.Test;
//import java.util.Date;
//import static org.junit.jupiter.api.Assertions.*;
///**
// * Test class used to test the SimulationParameters class to make sure it works as intended
// * */
//class SimulationParametersTest {
//    private Parameters testParam;
//
//
//    @BeforeEach
//    void BeforeEachSimulationPerametersTest() {
//        testParam = new Parameters();
//    }
//
//    @Test
//    void should_add_user() {
//        Parameters testParam = new Parameters(new Date(), "TestLocation");
//        testParam.addUser("Rick", "Parent");
//        assertEquals(1, testParam.getUsers().size());
//        testParam.addUser("Peter", "Child");
//        assertEquals(2, testParam.getUsers().size());
//    }
//
//    @Test
//    void should_remove_user_with_corresponding_id() {
//        Parameters testParam = new Parameters(new Date(), "TestLocation");
//        testParam.addUser("Rick", "Parent");
//        testParam.addUser("Peter", "Child");
//        assertEquals(2, testParam.getUsers().size());
//        testParam.removeUser(testParam.getUsers().get(0).getId());
//        assertEquals(1, testParam.getUsers().size());
//        assertEquals("Peter", testParam.getUsers().get(0).getName());
//    }
//
//    @Test
//    void should_change_date() {
//        Parameters testParam = new Parameters(new Date(), "TestLocation");
//        testParam.setDate(new Date(1302979200));
//        Date expected = new Date(1302979200);
//        assertEquals(expected, testParam.getDate());
//    }
//
//    @Test
//    void should_change_location() {
//        Parameters testParam = new Parameters(new Date(), "TestLocation");
//        assertEquals("TestLocation", testParam.getLocation());
//        testParam.setLocation("NewLocation");
//        assertEquals("NewLocation", testParam.getLocation());
//
//    }
//
//    @Test
//    void should_set_loggedInAs_to_a_user() {
//        Parameters testParam = new Parameters(new Date(), "TestLocation");
//        testParam.addUser("Rick", "Parent");
//        testParam.addUser("Peter", "Child");
//        testParam.logInAs(testParam.getUsers().get(0).getId());
//        assertEquals("Rick", testParam.getLoggedAs().getName());
//    }
//}