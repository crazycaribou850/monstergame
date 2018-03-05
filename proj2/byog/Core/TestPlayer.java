package byog.Core;

import byog.TileEngine.TERenderer;

public class TestPlayer {
    public static void testInsertPlayer() {
        TERenderer ter = new TERenderer();
        ter.initialize(30, 30);

        World myWorld = new World(30, 123);
        myWorld.generateWorld(20, 10);

        Player p = new Player(new Game());
        p.insertPlayer(myWorld);

        ter.renderFrame(myWorld.world);
    }

    public static void main(String[] args) {
        testInsertPlayer();//passes
    }
}
