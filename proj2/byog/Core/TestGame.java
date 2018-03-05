package byog.Core;

import byog.TileEngine.TETile;

public class TestGame {
    public static void playWithInputStringTest() {
        Game newgame = new Game();
        TETile[][] world = newgame.playWithInputString("N1219swdwd");
        newgame.ter.initialize(newgame.WIDTH, newgame.HEIGHT + 4);
        newgame.ter.renderFrame(world);
        //newgame.ter.renderFrame(newgame.playWithInputString("N999s"));
    }

    public static void playWithInputStringTest2() {
        Game newgame = new Game();
        newgame.playWithInputString("N1219s:Q");
        TETile[][] world2 = newgame.playWithInputString("Lwdwd");
        newgame.ter.initialize(newgame.WIDTH, newgame.HEIGHT + 4);
        newgame.ter.renderFrame(world2);
        //newgame.ter.renderFrame(newgame.playWithInputString("N999s"));
    }

    public static void playWithInputStringTestAG() {
        Game newgame = new Game();
        //TETile[][] world = newgame.playWithInputString("n6547766204324870169ssdswa");
        //TETile [][] world = newgame.playWithInputString(("n1914164012418174419saaadd"));
        TETile [][] world = newgame.playWithInputString(("n1914164022418174419saaadd"));
        newgame.ter.initialize(newgame.WIDTH, newgame.HEIGHT + 4);
        newgame.ter.renderFrame(world);
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

    public static void playWithKeyboardInputTest() {
        Game newgame = new Game();
        newgame.playWithKeyboard();
    }

    public static void main(String[] args) {
        //playWithInputStringTest2();
        //playWithInputStringTest();
        //* drawStartFrameTest();
        //startScreenTest();
        //playWithKeyboardInputTest();
        playWithInputStringTestAG();
    }
}
