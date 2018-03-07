package byog.Core;

import byog.TileEngine.TETile;

import java.awt.*;

public class Tilespawn {

    public static TETile spawnFloor() {
        return new TETile('·', new Color(128, 192, 128), new Color(128, 192, 128), "floor");
    }
    public static TETile spawnWarp() {
        return new TETile('▒', Color.yellow, Color.black, "warp");
    }
    public static TETile spawnCoin() {
        return new TETile('0', Color.yellow, new Color(128, 192, 128), "coin");
    }
    public static TETile spawnMonster() {
        return new TETile('♠', Color.black, Color.red, "IM GONNA GET U");
    }
}
