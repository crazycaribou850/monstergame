package byog.Core;

import byog.Core.Game;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

public class TestHUD {

    /**
     * Prints out x,y coordinates of mouse in blank canvas
     */
    public static void readMouseonCanvas() {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.show();

        //Press any key to stop
        while (!StdDraw.hasNextKeyTyped()) {
            int xPos = (int) StdDraw.mouseX(); //Casting rounds down
            int yPos = (int) StdDraw.mouseY();
            int[] coor = {xPos, yPos};
            System.out.println(StdDraw.mouseX() + ", " + StdDraw.mouseY());
        }
    }

    public static void readMouseinWorld() {
        //generate & render a World
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        World myWorld = new World(30, 999);
        myWorld.generateWorld(20, 10);

        ter.renderFrame(myWorld.world);

        //Display mouse coordinates (press any key to stop)
        while (!StdDraw.hasNextKeyTyped()) {
            int xPos = (int) StdDraw.mouseX(); //Casting rounds down
            int yPos = (int) StdDraw.mouseY();
            System.out.println(xPos + ", " + yPos);
        }
    }

    public static void readMouse4TileTest() {
        //generate & render a World
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        World myWorld = new World(30, 999);
        myWorld.generateWorld(20, 10);

        ter.renderFrame(myWorld.world);

        //Display TETile.description under mouse (press any key to stop)
        while (!StdDraw.hasNextKeyTyped()) {

            System.out.println(Game.readMouse4Tile(myWorld));
        }
    }

    public static void main(String[] args) {
        //readMouseonCanvas(); //To see how StdDraw.mouseX/Y() works
        //readMouseinWorld(); //To see how StdDraw.mouseX/Y() works
        readMouse4TileTest(); //Works
    }
}
