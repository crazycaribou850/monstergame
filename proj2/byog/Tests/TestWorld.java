package byog.Tests;

import byog.Core.World;
import byog.Core.Room;
import byog.TileEngine.TERenderer;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestWorld {

    public static void testCreateRoom() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        World myWorld = new World(30);
        Room randRoom = myWorld.createRoom();

        ter.renderFrame(randRoom.room);
    }

    @Test
    public void testIsOccupied() {
        World myWorld = new World(30);
        assertFalse(myWorld.isOccupied(0, 0, 30, 30));

        Room myRoom = new Room(5, 5, 0, 0);
        myWorld.insertRoom(myRoom);
        assertTrue(myWorld.isOccupied(0,0,5,5));
        assertTrue(myWorld.isOccupied(0,0,1,0));

    }

    public static void testEmptyWorld() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);
        World myWorld = new World(30);
        ter.renderFrame(myWorld.world);
    }

    public static void testInsertRoomNoHalls() {
        TERenderer ter = new TERenderer();
        ter.initialize(10, 10);

        World myWorld = new World(5);
        Room myRoom = new Room(3, 3, 2, 2);
        myWorld.insertRoom(myRoom);

        ter.renderFrame(myWorld.world);
    }

    public static void testInsertMultipleRooms() {
        TERenderer ter = new TERenderer();
        ter.initialize(20, 20);

        World myWorld = new World(20);
        Room RoomOne = new Room(3, 3, 2, 2);
        myWorld.insertRoom(RoomOne);

        Room RoomTwo = new Room(5, 5, 4, 7);
        myWorld.insertRoom(RoomTwo);

        /** Overlap should not show up in myWorld */
        Room Overlap = new Room(10, 10, 0, 0);
        myWorld.insertRoom(RoomTwo);

        ter.renderFrame(myWorld.world);
    }

    public static void main(String[] args) {
        //testCreateRoom(); //Passes
        //testEmptyWorld(); //Passes
        //testInsertRoomNoHalls(); //Passes
        //testInsertMultipleRooms(); //Passes
    }
}
