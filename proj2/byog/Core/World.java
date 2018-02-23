package byog.Core;

import byog.Core.Hallway;
import byog.Core.Room;
import byog.Core.RandomUtils;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class World {
    public TETile[][] world;
    public int size;

    private Room prev; // Previously inserted room
    private int failures; // After # consecutive failures, stop inserting Rooms

    public World (int s) {
        world = buildWorld(s);

        size = s;
        prev = null;
        failures = 0;
    }

    private TETile[][] buildWorld(int s) {
        TETile[][] thisWorld = new TETile[s][s];
        for (int x = 0; x < s; x += 1) {
            for (int y = 0; y < s; y += 1) {
                thisWorld[x][y] = Tileset.NOTHING;
            }
        }
        return thisWorld;
    }

    /** Creates random sized room with random lat/longs */
    public Room createRoom() {
        Random r = new Random();
        int rWidth = RandomUtils.uniform(r, 3, size / 2);
        int rHeight = RandomUtils.uniform(r, 3, size / 2);
        int rLat = RandomUtils.uniform(r, world[0].length - rWidth);
        int rLng = RandomUtils.uniform(r, world.length - rHeight);

        Room room = new Room(rWidth, rHeight, rLat, rLng);

        return room;
    }

    /** Attempts to insert Room object into this.world,
     * if successful: prev = room, failures = 0, insert hallway
     * if fail: failure += 1
     */
    public void insertRoom(Room room) {
        int xi = room.latitude;
        int yi = room.longitude;

        /** If there's room to put a Room (haha). This overrides Hallways. */
        if (!isOccupied(xi, yi, room.width, room.height)) {

            for (int x = 0; x < room.width; x += 1) {
                for (int y = 0; y < room.height; y += 1) {
                    world[x + xi][y + yi] = room.room[x][y];
                }
            }

            //insertHall();

            prev = room;
            failures = 0;

        } else {
            failures += 1;
        }
    }

    public boolean isOccupied(int startX, int startY, int width, int height) {
        for (int x = startX; x < startX + width; x += 1) {
            for (int y = startY; y < startY + height; y += 1) {
                if (world[x][y] == Tileset.WALL) {
                    return true;
                }
            }
        }
        return false;
    }

    public void insertHall() {
        return;
    }

    public void GenerateWorld() {
        return;
    }

}
