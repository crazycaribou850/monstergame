package byog.Core;

import byog.TileEngine.TERenderer;

import static org.junit.Assert.*;

public class TestWorld {

    public static void testCreateRoom() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        World myWorld = new World(30, 999);
        Room randRoom = myWorld.createRoom();

        ter.renderFrame(randRoom.room);
    }

    public void testIsOccupied() {
        World myWorld = new World(30, 999);
        assertFalse(myWorld.isOccupied(0, 0, 30, 30));

        Room myRoom = new Room(5, 5, 0, 0);
        myWorld.insertRoom(myRoom);
        assertTrue(myWorld.isOccupied(0, 0, 5, 5));
        assertTrue(myWorld.isOccupied(0, 0, 1, 1));
        /** Test that returns False for non colliding proposed room */
        assertFalse(myWorld.isOccupied(6, 6, 12, 12));
        /** Test that proposed room is within map boundaries */
        assertTrue(myWorld.isOccupied(24, 24, 7, 7));

    }

    public static void testEmptyWorld() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);
        World myWorld = new World(30, 999);
        ter.renderFrame(myWorld.world);
    }

    public static void testInsertRoomNoHalls() {
        TERenderer ter = new TERenderer();
        ter.initialize(10, 10);

        World myWorld = new World(5, 999);
        Room myRoom = new Room(3, 3, 2, 2);
        myWorld.insertRoom(myRoom);

        ter.renderFrame(myWorld.world);
    }

    public static void testInsertMultipleRooms() {
        TERenderer ter = new TERenderer();
        ter.initialize(20, 20);

        World myWorld = new World(20, 999);
        Room roomOne = new Room(3, 3, 2, 2);
        myWorld.insertRoom(roomOne);

        Room roomTwo = new Room(5, 5, 4, 7);
        myWorld.insertRoom(roomTwo);

        /** overlap should not show up in myWorld */
        Room overlap = new Room(10, 10, 0, 0);
        myWorld.insertRoom(roomTwo);

        ter.renderFrame(myWorld.world);
    }

    public static void testInsertHallway() {
        TERenderer ter = new TERenderer();
        ter.initialize(20, 20);

        World myWorld = new World(20, 999);
        Room roomOne = new Room(3, 3, 2, 2);
        myWorld.insertRoom(roomOne);

        Room roomTwo = new Room(5, 5, 4, 7);
        myWorld.insertRoom(roomTwo);
        myWorld.insertHall(roomOne, roomTwo);

        Room roomThree = new Room(6, 6, 13, 4);
        myWorld.insertRoom(roomThree);
        myWorld.insertHall(roomTwo, roomThree);
        ter.renderFrame(myWorld.world);
    }

    public static void testBuildOverHallway() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        World myWorld = new World(30, 999);
        Room roomOne = new Room(3, 3, 1, 1);
        myWorld.insertRoom(roomOne);

        Room roomTwo = new Room(6, 6, 24, 24);
        myWorld.insertRoom(roomTwo);
        myWorld.insertHall(roomOne, roomTwo);

        Room roomThree = new Room(6, 6, 1, 24);
        myWorld.insertRoom(roomThree);

        ter.renderFrame(myWorld.world);
    }

    public static void testGenerateWorld() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        World myWorld = new World(30, 999);
        myWorld.generateWorld(20, 10);

        ter.renderFrame(myWorld.world);

    }

    public static void testWallified() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        World myWorld = new World(60, 999);
        myWorld.generateWorld(50, 20);
        myWorld.wallify();

        ter.renderFrame(myWorld.world);
    }

    public static void testRoomAtEdge() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        World myWorld = new World(30, 999);
        Room myRoom = new Room(5, 5, 25, 5);

        /** Right Wall goes past edge of world, should not show up */
        Room badRoom = new Room(5, 5, 26, 20);

        myWorld.insertRoom(myRoom);
        myWorld.insertRoom(badRoom);

        ter.renderFrame(myWorld.world);
    }

    public static void main(String[] args) {
        //testCreateRoom(); //Passes
        //testEmptyWorld(); //Passes
        //testInsertRoomNoHalls(); //Passes
        //testInsertMultipleRooms(); //Passes
        //testInsertHallway(); //Passes
        //testBuildOverHallway(); //Passes
        //testGenerateWorld(); //Buggy
        testWallified();
        //testRoomAtEdge(); //Passes
    }
}
