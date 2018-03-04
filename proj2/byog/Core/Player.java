package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;
import java.util.Random;

public class Player implements Serializable{
    int xPos;
    int yPos;
    int coins;
    boolean dead;
    Game game;
    World world;
    TETile type;
    TETile current;
    String description = "PERSON";

    public Player(Game g) {
        xPos = -1;
        yPos = -1;
        type = Tileset.PLAYER;
        current = null;
        coins = 0;
        dead = false;
        this.game = g;
    }

    public Player(int i, Game g) {
        xPos = -1;
        yPos = -1;
        type = Tileset.MONSTER;
        current = null;
        coins = 0;
        dead = false;
        this.game = g;
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
        this.world = myWorld;
        while (xPos < 0) {
            int randX = RandomUtils.uniform(rand, 0, world[0].length);
            int randY = RandomUtils.uniform(rand, 0, world[0].length);

            if (world[randX][randY] != Tileset.WALL && world[randX][randY] != Tileset.NOTHING) {
                this.current = world[randX][randY];
                world[randX][randY] = type;
                this.xPos = randX;
                this.yPos = randY;
                return;
            }
        }
    }

    public void interaction(TETile tile) {
        if (tile == Tileset.FLOOR) {
            return;
        }
        if (tile == Tileset.COIN) {
            this.coins += 1;
        }
        if (tile == Tileset.MONSTER) {
            this.dead = true;
            game.gameOver = true;
        }
        return;
    }
}
