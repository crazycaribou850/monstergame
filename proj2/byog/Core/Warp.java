package byog.Core;

import byog.Core.Game;
import byog.Core.World;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

/**
 * For ambition points
 * Description: a warp room is indicated by WARP tiles. If a player is in a
 * warp room, pressing 'O' will transport them to another warp room.

 * Implementation strategy: after world is generated with rooms/hallways:

 * Warp constructor (creates a warp room)
 * iterate through (starting from bottom left) to find a room (i.e. enclosed
 * by walls), then double for loop to change tile type to WARP.

 * Warp room bottom-left & top-right coordinates will be remembered in a World
 * instance variable to allow interaction
 */
public class Warp implements Serializable {
    World world;
    TETile[][] wTiles;

    TETile type = Tileset.SAND;
    String description = "WARP";
    String name;

    /** bottom left */
    int xStart;
    int yStart;

    /** top right */
    int xEnd;
    int yEnd;

    // Create warp room from 0, 0
    public Warp(World myWorld, String name) {
        world = myWorld;
        wTiles = world.world;

        int[] roomCoord = findRoom(0, 0);
        xStart = roomCoord[0];
        yStart = roomCoord[1];
        xEnd = roomCoord[2];
        yEnd = roomCoord[3];

        insertWarp();
        myWorld.warps.put(name, this);
    }

    // Create warp room to the above yinit
    public Warp(World myWorld, String name, int y) {
        world = myWorld;
        wTiles = world.world;

        int[] roomCoord = findRoom(0, y);
        xStart = roomCoord[0];
        yStart = roomCoord[1];
        xEnd = roomCoord[2];
        yEnd = roomCoord[3];

        insertWarp();
        myWorld.warps.put(name, this);
    }

    /** Returns 4 element int array of coordinates ([startx, starty, endx, endy])
     * of first room starting at xStart, yStart then searching to the right and up */
    public int[] findRoom(int xinit, int yinit) {
        int[] start = findStart(xinit, yinit);
        xStart = start[0];
        yStart = start[1];

        //Base case: if reach end of row, move up
        if (endOfRow(xStart, yStart)) {
            return findRoom(0, yStart + 1);
        }

        //Find end x-coordinate
        xEnd = xStart;
        while (!wTiles[xEnd + 1][yStart].description.equals("wall")) {
            xEnd += 1;
        }
        //Fix start y coordinate
        while (!wTiles[xStart][yStart - 1].description.equals("wall")
                && !wTiles[xStart][yStart - 1].description.equals("nothing")) {
            yStart -= 1;
        }
        //Find end y coordinate
        yEnd = yStart;
        while (!wTiles[xStart][yEnd + 1].description.equals("wall")) {
            yEnd += 1;
        }

        //Test these coordinates. If passes, return.
        if (findRoomHelper(xStart, yStart, xEnd, yEnd)) {
            int[] toReturn = {xStart, yStart, xEnd, yEnd};
            return toReturn;
        } //Else, keep searching.
        return findRoom(xStart + 1, yinit);
    }

    public void insertWarp() {
        for (int x = xStart; x <= xEnd; x += 1) {
            for (int y = yStart; y <= yEnd; y += 1) {
                wTiles[x][y] = type; //SAND
            }
        }
    }

    /****************** Helper Methods ************************/

    /** Test if there's a room contained or if it's a hallway */
    public boolean findRoomHelper(int xStart, int yStart, int xEnd, int yEnd) {
        if (xStart == xEnd || yStart == yEnd) {
            return false;
        }
        for (int x = xStart; x < xEnd; x += 1) {
            for (int y = yStart; y < yEnd; y += 1) {
                if (wTiles[x][y].description.equals("wall")) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returned array[0], array[1] are x, y coordinates of first FLOOR
     * tile starting at xinit, yinit then search to the right and up */
    public int[] findStart(int xinit, int yinit) {
        for (int y = yinit; y < wTiles[0].length - 1; y += 1) {
            for (int x = xinit; x < wTiles.length - 1; x += 1) {
                if (wTiles[x][y].description.equals("floor")) {
                    int[] toRet = {x, y};
                    return toRet;
                }
            }
        }
        return null; // = somethings wrong
    }

    /** Returns true if wTiles[x][y] is last FLOOR in row */
    public boolean endOfRow(int xS, int y) {
        for (int x = xS + 2; x < wTiles.length; x += 1) {
            if (!wTiles[x][y].description.equals("nothing")) {
                return false;
            }
        }
        return true;
    }

    /************************ ^^^^ **************************/

}
