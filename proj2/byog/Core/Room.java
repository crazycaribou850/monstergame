package byog.Core;

import java.io.Serializable;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Room implements Serializable{
    TETile[][] room;

    /** Tile type for room's perimeter (default: WALL) */
    TETile pType;

    /** Tile type for room's floors (default: FLOOR) */
    TETile type;

    int width;
    int height;

    /** bottom left */
    int latitude;
    int longitude;

    /** center[0], center[1] = latitude, longitude of center */
    int[] center;

    public Room(int w, int h, int lat, int lng) {
        pType = Tileset.WALL;
        type = Tileset.FLOOR;

        width = w;
        height = h;

        latitude = lat;
        longitude = lng;

        center = new int[2];
        center[0] = lat + w / 2;
        center[1] = lng + h / 2;

        room = buildRoom(w, h);
    }

    private TETile[][] buildRoom(int w, int h) {
        TETile[][] myRoom = new TETile[w][h];

        for (int x = 0; x < w; x += 1) {
            for (int y = 1; y < h; y += 1) {
                myRoom[x][y] = this.type;
            }
        }

        /** Top & Bottom Walls */
        for (int x = 0; x < w; x += 1) {
            myRoom[x][0] = this.pType;
            myRoom[x][h - 1] = this.pType;
        }

        /** Left & Right Walls */
        for (int y = 0; y < h; y += 1) {
            myRoom[0][y] = this.pType;
            myRoom[w - 1][y] = this.pType;
        }

        return myRoom;
    }
}



