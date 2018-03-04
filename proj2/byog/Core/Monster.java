package byog.Core;

import byog.TileEngine.*;
import java.io.Serializable;
import java.util.Random;

public class Monster implements Serializable{
    int xPos;
    int yPos;
    int coins;
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
        coins = 0;
        dead = false;
        this.game = g;
        this.atk = atk;
    }

    public void insertMonster(World myWorld) {
        TETile[][] world = myWorld.world;
        Random rand = myWorld.random;
        this.world = myWorld;
        while (xPos < 0) {
            int randX = RandomUtils.uniform(rand, 0, world[0].length);
            int randY = RandomUtils.uniform(rand, 0, world[0].length);
            // Ensures that monsters do not spawn within 1 tile of Player.
            if (world[randX][randY] != Tileset.WALL && world[randX][randY] != Tileset.NOTHING) {
                for (int i = randX - 1; i <= randX + 1; i++) {
                    for (int j = randY - 1; j <= randY + 1; j++) {
                        if (world[i][j] == Tileset.PLAYER) {
                            continue;
                        }
                    }
                }
                this.current = world[randX][randY];
                world[randX][randY] = type;
                xPos = randX;
                yPos = randY;
                return;
            }
        }
    }

    public void interaction(TETile tile) {
        if (tile == Tileset.FLOOR || tile == Tileset.COIN) {
            return;
        }
        if (tile == Tileset.PLAYER) {
            game.gameOver = true;
        }
        return;
    }
}
