package byog.Core;
import java.io.Serializable;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class World implements Serializable{
    TETile[][] world;
    int size;

    private Room prev; // Previously inserted room
    private Room prevprev; //Previously Previously inserted room
    private int failures; // After # consecutive failures, stop inserting Rooms
    Random random;
    Coin[] coins;
    Monster[] monsters;
    Player player;

    public World(int s, long seed) {
        world = buildWorld(s);
        size = s;
        prev = null;
        prevprev = null;
        failures = 0;
        random = new Random(seed);
        coins = new Coin[5]; //Value can be changed later//
        monsters = new Monster[20]; //Default value: can be changed later //
    }

    public World(int s, long seed, int coins, int monsters) {
        world = buildWorld(s);
        size = s;
        prev = null;
        prevprev = null;
        failures = 0;
        random = new Random(seed);
        this.coins = new Coin[coins]; //Value can be changed later//
        this.monsters = new Monster[monsters];
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

    /**
     * Creates random sized room with random lat/longs
     */
    public Room createRoom() {
        int rWidth = RandomUtils.uniform(random, 4, size / 4);
        int rHeight = RandomUtils.uniform(random, 4, size / 4);
        int rLat = RandomUtils.uniform(random, world[0].length - rWidth);
        int rLng = RandomUtils.uniform(random, world.length - rHeight);

        Room room = new Room(rWidth, rHeight, rLat, rLng);

        return room;
    }

    /**
     * Attempts to insert Room object into this.world,
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
                    if (world[x + xi][y + yi] == Tileset.NOTHING) {
                        world[x + xi][y + yi] = room.room[x][y];
                    }
                }
            }

            prevprev = prev;
            prev = room;
            failures = 0;

        } else {
            failures += 1;
        }
    }

    public boolean isOccupied(int startX, int startY, int width, int height) {
        /* First check if the space requested is within the bounds of the map */
        if (((startX + width) > size) || ((startY + height) > size)) {
            return true;
        }
        for (int x = startX; x < startX + width; x += 1) {
            for (int y = startY; y < startY + height; y += 1) {
                if (world[x][y] == Tileset.WALL) {
                    return true;
                }
            }
        }
        return false;
    }

    // Very verbose and lengthy function that accounts for various cases://
    // 2 orientations given for each case for increased entropy //
    public void insertHall(Room A, Room B) {
        Hallway hallway = new Hallway(A, B);
        int orientation = RandomUtils.uniform(random, 0, 2);
        if ((A.latitude < B.latitude && A.longitude > B.longitude)
                || (B.latitude < A.latitude && B.longitude > A.latitude)) {
            orientation = orientation = orientation + 2;
        }
        /** Decide whether to build horizontally or vertically first randomly */
        if (orientation == 0) /* build horizontally first */ {
            for (int i = hallway.xInitial; i <= hallway.xFinal; i++) {
                world[i][hallway.yInitial] = hallway.horizontal[i - hallway.xInitial];
            }
            for (int i = hallway.yInitial; i <= hallway.yFinal; i++) {
                world[hallway.xFinal][i] = hallway.vertical[i - hallway.yInitial];
            }
        } else if (orientation == 1) {
            for (int i = hallway.yInitial; i <= hallway.yFinal; i++) {
                world[hallway.xInitial][i] = hallway.vertical[i - hallway.yInitial];
            }
            for (int i = hallway.xInitial; i <= hallway.xFinal; i++) {
                world[i][hallway.yFinal] = hallway.horizontal[i - hallway.xInitial];
            }
        } else if (orientation == 3) {
            // Account for special cases where A is XiYf and B is XfYi or vice versa
            for (int i = hallway.yInitial; i <= hallway.yFinal; i++) {
                world[hallway.xFinal][i] = hallway.vertical[i - hallway.yInitial];
            }
            for (int i = hallway.xInitial; i <= hallway.xFinal; i++) {
                world[i][hallway.yFinal] = hallway.horizontal[i - hallway.xInitial];
            }
        } else {
            for (int i = hallway.xFinal; i >= hallway.xInitial; i--) {
                world[i][hallway.yInitial] = hallway.horizontal[i - hallway.xInitial];
            }
            for (int i = hallway.yFinal; i >= hallway.yInitial; i--) {
                world[hallway.xInitial][i] = hallway.vertical[i - hallway.yInitial];
            }
        }
        return;
    }

    // Helper method for wallify function to check if a location bounds a floor tile
    private boolean shouldBeWall(int x, int y) {
        if (world[x][y] == Tileset.NOTHING) {
            if (x + 1 != size && (world[x + 1][y] == Tileset.FLOOR)
                    || (world[Math.abs(x - 1)][y] == Tileset.FLOOR)) {
                return true;
            }
            if (y + 1 != size && (world[x][y + 1] == Tileset.FLOOR)
                    || (world[x][Math.abs(y - 1)] == Tileset.FLOOR)) {
                return true;
            }
        }
        return false;
    }

    // Method to surround all floor tiles with walls
    public void wallify() {
        for (int x = 0; x < size; x += 1) {
            for (int y = 0; y < size; y += 1) {
                if (shouldBeWall(x, y)) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

    public void generateWorld(int attempts, int maxfailure) {
        while (attempts > 0 && failures < maxfailure) {
            Room newRoom = createRoom();
            insertRoom(newRoom);
            if (prevprev != null) {
                insertHall(prev, prevprev);
            }
            attempts = attempts - 1;
        }
        wallify(); //Uncomment to turn on wallify//
        //p = new Player();
        //p.insert(world);
        return;
    }

}
