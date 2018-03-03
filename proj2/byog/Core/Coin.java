package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Coin {
    int value = 1;
    int xPos = -1;
    int yPos = -1;
    TETile type = Tileset.FLOWER;
    TETile current = null;


    public void insertCoin(World myWorld) {
        TETile[][] world = myWorld.world;
        Random rand = myWorld.random;

        while (this.xPos < 0) {
            for (int y = 0; y < world[1].length; y += 1) {
                int randX = RandomUtils.uniform(rand, 0, world[0].length);
                int randY = RandomUtils.uniform(rand, 0, world[0].length);

                if (world[randX][randY] != Tileset.WALL && world[randX][randY] != Tileset.NOTHING) {
                    this.current = world[randX][randY];
                    world[randX][randY] = type;
                    xPos = randX;
                    yPos = randY;
                    return;
                }
            }
        }
    }
}
