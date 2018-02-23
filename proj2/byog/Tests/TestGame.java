package byog.Tests;

import byog.Core.Game;

public class TestGame {
    public static void basicTest() {
        Game newgame = new Game();
        newgame.ter.initialize(50,50);

        newgame.ter.renderFrame(newgame.playWithInputString("N1219s"));
        //newgame.ter.renderFrame(newgame.playWithInputString("N999s"));
    }

    public static void main(String[] args) {
        basicTest(); // Works swell.
    }
}
