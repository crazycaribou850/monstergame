package byog.Core;

import byog.Core.Room;
import byog.TileEngine.TERenderer;

public class TestRoom {

    public static void testSimpleRoom() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        Room myRoom = new Room(30, 30, 0, 0);

        ter.renderFrame(myRoom.room);
    }

    public static void testWeirdRoom() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        Room myRoom = new Room(2, 9, 0, 0);

        ter.renderFrame(myRoom.room);
    }

    public static void main(String[] args) {
        //testSimpleRoom(); // Passes
        //testWeirdRoom(); //Passes
    }
}
