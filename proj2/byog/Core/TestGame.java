package byog.Core;

import byog.Core.Game;

public class TestGame {
    public static void basicTest() {
        Game newgame = new Game();
        newgame.ter.initialize(50, 50);

        newgame.ter.renderFrame(newgame.playWithInputString("N1219s"));
        //newgame.ter.renderFrame(newgame.playWithInputString("N999s"));
    }

    public static void drawStartFrameTest() {
        Game newgame = new Game();
        newgame.drawStartFrame();
        //newgame.drawGameOverFrame();
    }

    public static void startScreenTest() {
        Game newgame = new Game();
        newgame.startScreen();
    }

    public static void main(String[] args) {
        //* basicTest(); // Works swell.
        //* drawStartFrameTest(); //Works swell
        startScreenTest();
    }
}