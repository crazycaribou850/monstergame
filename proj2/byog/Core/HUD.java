package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

public class HUD {
    public int[] readMouse() {
        int xPos = (int) StdDraw.mouseX(); //Casting rounds down
        int yPos = (int) StdDraw.mouseY();
        int[] coor = {xPos, yPos};
        return coor;
    }
}
