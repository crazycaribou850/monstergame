package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class Coin implements Serializable {
    int value = 1;
    int xPos = -1;
    int yPos = -1;
    TETile type;
    TETile current = null;
    World world;


    public void insertCoin(World myWorld) {
        TETile[][] worldArray = myWorld.world;
        Random rand = myWorld.random;
        this.world = myWorld;
        while (this.xPos < 0) {
            int randX = RandomUtils.uniform(rand, 0, worldArray[0].length);
            int randY = RandomUtils.uniform(rand, 0, worldArray[0].length);

            if (worldArray[randX][randY].description().equals("floor")) {
                this.current = worldArray[randX][randY];
                worldArray[randX][randY] = Tilespawn.spawnCoin();
                xPos = randX;
                yPos = randY;
                return;
            }
        }
    }
}
