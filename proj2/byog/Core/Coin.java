package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;
import java.util.Random;

public class Coin implements Serializable{
    int value = 1;
    int xPos = -1;
    int yPos = -1;
    TETile type = Tileset.COIN;
    TETile current = null;
    World world;


    public void insertCoin(World myWorld) {
        TETile[][] world = myWorld.world;
        Random rand = myWorld.random;
        this.world = myWorld;
        while (this.xPos < 0) {
            int randX = RandomUtils.uniform(rand, 0, world[0].length);
            int randY = RandomUtils.uniform(rand, 0, world[0].length);

            if (world[randX][randY].description == "floor") {
                this.current = world[randX][randY];
                world[randX][randY] = type;
                xPos = randX;
                yPos = randY;
                return;
            }
        }
    }
}
