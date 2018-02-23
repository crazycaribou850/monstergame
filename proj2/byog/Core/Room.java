package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Room {
    public TETile[][] room;
    public TETile Ptype;
    /**
     * Tile type for room's perimeter (default: WALL)
     */
    public TETile type;
    /**
     * Tile type for room's floors (default: FLOOR)
     */

    public int width;
    public int height;

    /**
     * Bottom left
     */
    public int latitude;
    public int longitude;

    public int[] center;

    /**
     * center[0], center[1] = latitude, longitude of center
     */

    public Room(int w, int h, int lat, int lng) {
        Ptype = Tileset.WALL;
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
        TETile[][] room = new TETile[w][h];

        for (int x = 0; x < w; x += 1) {
            for (int y = 1; y < h; y += 1) {
                room[x][y] = this.type;
            }
        }

        /** Top & Bottom Walls */
        for (int x = 0; x < w; x += 1) {
            room[x][0] = this.Ptype;
            room[x][h - 1] = this.Ptype;
        }

        /** Left & Right Walls */
        for (int y = 0; y < h; y += 1) {
            room[0][y] = this.Ptype;
            room[w - 1][y] = this.Ptype;
        }

        return room;
    }
}



