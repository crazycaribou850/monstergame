package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Player {
    int xPos;
    int yPos;
    TETile type;
    TETile current;

    public Player() {
        xPos = -1;
        yPos = -1;
        type = Tileset.PLAYER;
        current = null;
    }

//    public Player(Tileset t) {
//        xPos = -1;
//        yPos = -1;
//        type = t;
//    }

    /** Strategy: randomly insert until on Hallway tile */
    public void insertPlayer(World myWorld) {
        TETile[][] world = myWorld.world;
        Random rand = myWorld.random;

        while (xPos < 0) {
            for (int y = 0; y < world[1].length; y += 1) {
                int randX = RandomUtils.uniform(rand, 0, world[0].length);

                if (world[randX][y] != Tileset.WALL && world[randX][y] != Tileset.NOTHING) {
                    this.current = world[randX][y];
                    world[randX][y] = type;
                    xPos = randX;
                    yPos = y;
                    return;
                }
            }
        }
    }
}
