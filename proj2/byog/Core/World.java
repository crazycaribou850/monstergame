package byog.Core;

import byog.Core.Hallway;
import byog.Core.Room;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class World {
    public int size;

    private Room prev; // Previously inserted room
    private Room failures; // After # consecutive failures, stop inserting Rooms
}
