package smarthome.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void ID_Increment_Test() {
        User user0 = new User("John", "Parent");
        User user1 = new User("Jack", "Child");
        User user2 = new User("Jill", "Stranger");
        User user3 = new User("Jones", "Guest");

        assertEquals(user0.getId() + 1, user1.getId());
        assertEquals(user1.getId() + 1, user2.getId());
        assertEquals(user2.getId() + 1, user3.getId());
    }
}
