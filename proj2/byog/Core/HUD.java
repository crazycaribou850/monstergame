package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

public class HUD {
    /** Reads position of mouse and returns Tile type under it if exists */
//    public void readMouse4Tile() {
//        double xPos = StdDraw.mouseX();
//        double yPos = StdDraw.mouseY();
//    }

    public int[] readMouse() {
        int xPos = (int) StdDraw.mouseX(); //Casting rounds down
        int yPos = (int) StdDraw.mouseY();
        int[] coor = {xPos, yPos};
        return coor;
    }
}
