package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Hallway {
    /** always plots left and upwards */
    TETile type;
    TETile[] horizontal;
    TETile[] vertical;

    /** the x coordinate of the middle of the lefter object */
    int xInitial;
    /** the y coordinate of the middle of the downer object */
    int yInitial;

    int xFinal;
    int yFinal;
    int xLength;
    int yLength;

    /** builds a hallway to be placed at location */
    public Hallway(Room A, Room B, TETile type) {
        xInitial = Math.min(A.center[0], B.center[0]);
        yInitial = Math.min(A.center[1], B.center[1]);
        xFinal = Math.max(A.center[0], B.center[0]);
        yFinal = Math.max(A.center[1], B.center[1]);
        xLength = xFinal - xInitial;
        yLength = yFinal - yLength;
        this.type = type;
        horizontal = new TETile[xLength];
        for (int i = 0; i < xLength; i++) {
            horizontal[i] = type;
        }
        vertical = new TETile[yLength];
        for (int i = 0; i < yLength; i++) {
            vertical[i] = type;
        }
    }

    public Hallway(Room A, Room B) {
        xInitial = Math.min(A.center[0], B.center[0]);
        yInitial = Math.min(A.center[1], B.center[1]);
        xFinal = Math.max(A.center[0], B.center[0]);
        yFinal = Math.max(A.center[1], B.center[1]);
        xLength = xFinal - xInitial + 1;
        yLength = yFinal - yInitial + 1;
        this.type = Tileset.FLOOR;
        horizontal = new TETile[xLength];
        for (int i = 0; i < xLength; i++) {
            horizontal[i] = Tileset.FLOOR;
        }
        vertical = new TETile[yLength];
        for (int i = 0; i < yLength; i++) {
            vertical[i] = Tileset.FLOOR;
        }
    }
}
