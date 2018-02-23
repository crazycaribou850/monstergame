package byog.Tests;

import byog.Core.Game;

public class TestGame {
    public static void basicTest() {
        Game newgame = new Game();
        newgame.ter.initialize(100,100);

        newgame.ter.renderFrame(newgame.playWithInputString("N999s"));
    }

    public static void main(String[] args) {
        basicTest(); // Works swell.
    }
}
