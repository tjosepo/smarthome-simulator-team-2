package smarthome.controllers;

import org.junit.jupiter.api.Test;
import smarthome.models.LayoutFile;
import smarthome.models.Room;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class used to test the HouseLayout class to make sure it works as intended
 * */
public class HouseLayoutTests {
    private static House house = new House();

    @Test
    void readLayoutFile_Test() {
        LayoutFile layoutFile = new LayoutFile();
        layoutFile.rooms = new LayoutFile.RoomLayout[] {
                new LayoutFile.RoomLayout("Room 0", 0, 0, 1, 1, 1, 2, 3)
        };

        ArrayList<Room> rooms = (ArrayList<Room>) house.readLayoutFile(layoutFile);
        Room room0 = rooms.get(0);

        assertEquals("Room 0", room0.getName());
        assertEquals(0, room0.x);
        assertEquals(0, room0.y);
        assertEquals(1, room0.width);
        assertEquals(1, room0.width);
        assertEquals(1, room0.getWindows().size());
        assertEquals(2, room0.getDoors().size());
        assertEquals(3, room0.getLights().size());
    }
}
