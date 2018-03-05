package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class Monster implements Serializable {
    int xPos;
    int yPos;
    int atk;
    boolean dead;
    Game game;
    World world;
    TETile type;
    TETile current;

    public Monster(int atk, Game g) {
        xPos = -1;
        yPos = -1;
        type = Tileset.MONSTER;
        current = null;
        dead = false;
        this.game = g;
        this.atk = atk;
    }

    public void insertMonster(World myWorld) {
        TETile[][] worldArray = myWorld.world;
        Random rand = myWorld.random;
        this.world = myWorld;
        while (xPos < 0) {
            int randX = RandomUtils.uniform(rand, 0, worldArray[0].length);
            int randY = RandomUtils.uniform(rand, 0, worldArray[0].length);
            // Ensures that monsters do not spawn within 1 tile of Player.
            if ((worldArray[randX][randY].description.equals("floor"))) {
                this.current = worldArray[randX][randY];
                worldArray[randX][randY] = type;
                xPos = randX;
                yPos = randY;
                return;
            }
        }
    }

    public void interaction(TETile tile) {
        if (tile.description.equals("floor") || tile.description.equals("coin")) {
            return;
        }
        if (tile.description.equals("player")) {
            game.gameOver = true;
        }
        return;
    }
}
