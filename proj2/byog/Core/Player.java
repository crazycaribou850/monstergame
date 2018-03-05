package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class Player implements Serializable {
    int xPos;
    int yPos;
    int coins;
    boolean dead;
    Game game;
    World world;
    TETile type;
    TETile current;
    int bombs;
    String description = "PERSON";

    public Player(Game g) {
        xPos = -1;
        yPos = -1;
        type = Tileset.PLAYER;
        current = null;
        coins = 0;
        dead = false;
        this.game = g;
        bombs = 5;
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

    /**
     * Strategy: randomly insert until on Hallway tile
     */
    public void insertPlayer(World myWorld) {
        TETile[][] worldset = myWorld.world;
        Random rand = myWorld.random;
        this.world = myWorld;
        while (xPos < 0) {
            int randX = RandomUtils.uniform(rand, 0, worldset[0].length);
            int randY = RandomUtils.uniform(rand, 0, worldset[0].length);

            if (worldset[randX][randY].description().equals("floor")) {
                this.current = worldset[randX][randY];
                worldset[randX][randY] = type;
                this.xPos = randX;
                this.yPos = randY;
                return;
            }
        }
    }

    public void playerAttack() {
        bombs -= 1;
        for (int x = yPos; x >= yPos - coins; x--) {
            if (world.world[xPos][x].description().equals("IM GONNA GET U")) {
                world.world[xPos][x].inhabitant.takeDamage();
            }
            else if (world.world[xPos][x].description().equals("wall")) {
                break;
            }
        }
        for (int x = yPos; x <= yPos + coins; x++) {
            if (world.world[xPos][x].description().equals("IM GONNA GET U")) {
                world.world[xPos][x].inhabitant.takeDamage();
            }
            else if (world.world[xPos][x].description().equals("wall")) {
                break;
            }
        }
        for (int x = xPos; x >= xPos - coins; x--) {
            if (world.world[x][yPos].description().equals("IM GONNA GET U")) {
                world.world[x][yPos].inhabitant.takeDamage();
            }
            else if (world.world[x][yPos].description().equals("wall")) {
                break;
            }
        }
        for (int x = xPos; x <= xPos + coins; x++) {
            if (world.world[x][yPos].description().equals("IM GONNA GET U")) {
                world.world[x][yPos].inhabitant.takeDamage();
            }
            else if (world.world[x][yPos].description().equals("wall")) {
                break;
            }
        }
        for (int y = yPos-1; y <= yPos+1; y += 2) {
            for (int x = xPos-1; x <= xPos+1; x += 2) {
                if (this.world.world[x][y].description().equals("IM GONNA GET U")) {
                    this.world.world[x][y].inhabitant.takeDamage();
                }
            }
        }

    }

    public void interaction(TETile tile) {
        if (tile.description().equals("floor")) {
            return;
        }
        if (tile.description().equals("coin")) {
            this.coins += 1;
        }
        if (tile.description().equals("IM GONNA GET U")) {
            this.dead = true;
            game.gameOver = true;
        }
        if (tile.description().equals("bomb")) {
            this.bombs += 1;
        }
        return;
    }
}
