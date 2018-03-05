package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.TERenderer;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    boolean gameOver = false;
    int midWidth = WIDTH / 2;
    int midHeight = HEIGHT / 2;
    int seed;
    int colon = 0;
    World mainWorld;

    //*************************** DRAW METHODS ********************************\\

    // Initializes a Frame. May not be neccessary. //
    public void initializeFrame() {
        StdDraw.setCanvasSize(this.WIDTH * 16, this.HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    //ONLY DRAWS the Start Menu//
    public void drawStartFrame() {
        initializeFrame();
        /* Clears the frame and draws a new one using the stdDraw library tools */
        Font bigFont = new Font("Monaco", Font.BOLD, 50);
        Font smallFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight * (1.25), "GAME TITLE HERE");
        StdDraw.setFont(smallFont);
        StdDraw.text(midWidth, midHeight, "New Game (N)");
        StdDraw.text(midWidth, midHeight - 2.5, "Load Game (L)");
        StdDraw.text(midWidth, midHeight - 5, "Quit (Q)");
        StdDraw.show();
    }

    // Renders the frame that shows when the game is lost //
    public void drawGameOverFrame() {
        StdDraw.clear(Color.BLACK);
        Font bigFont = new Font("Monaco", Font.BOLD, 50);
        Font normalFont = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.red);
        StdDraw.text(midWidth, midHeight * (1.25), "GAME OVER");
        StdDraw.setFont(normalFont);
        StdDraw.text(midWidth, midHeight, "The monsters got you!");
        StdDraw.show();
    }

    // Renders the frame that shows when the game is won //
    public void drawWinFrame() {
        StdDraw.clear(Color.BLACK);
        Font bigFont = new Font("Monaco", Font.BOLD, 50);
        Font normalFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.green);
        StdDraw.text(midWidth, midHeight * (1.5), "CONGRATS!");
        StdDraw.text(midWidth, midHeight * (1.3), "YOU WIN!");
        StdDraw.setFont(normalFont);
        StdDraw.text(midWidth, midHeight, "YOU ARE CHAMPION COIN COLLECTOR");
        StdDraw.text(midWidth, midHeight * 0.9, "AND MASTER MONSTER EVADER");
        StdDraw.show();
    }

    //******************************* End of Draw Methods ************************************\\

    //**************** Initialisation Methods: Getting the game started **********************\\

    // Initializes the game by drawing the start screen and demanding user imput //
    public void startScreen() {
        drawStartFrame();
        char userInput = ' ';
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            userInput = StdDraw.nextKeyTyped();
            if (userInput == 'N' || userInput == 'n') {
                this.seed = requestSeed();
                this.mainWorld = new World(WIDTH, this.seed);
                StdDraw.pause(300);
                startGame();
                return;
            }
            if (userInput == 'L' || userInput == 'l') {
                Game loadGame = loadGame();
                loadGame.gameLoop();
                return;
            }
            if (userInput == 'Q' || userInput == 'q') {
                System.exit(0); //Closes the window
            } else {
                continue;
            }
        }

    }

    // Frame that requests a seed from the user //
    public int requestSeed() {
        StdDraw.clear(Color.BLACK);
        String dispString = "Seed: ";
        Font bigFont = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, dispString);
        StdDraw.text(midWidth, midHeight - 3, "Enter a seed# and press s to start");
        StdDraw.show();
        char key = ' ';
        String userNumber = "";
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            key = StdDraw.nextKeyTyped();
            if (Character.isDigit(key)) {
                userNumber += key;
                dispString += key;
                StdDraw.clear(Color.BLACK);
                StdDraw.text(midWidth, midHeight, dispString);
                StdDraw.text(midWidth, midHeight - 3, "Enter a seed# and press s to start");
                StdDraw.show();
            } else if (key == 's' || key == 'S') {
                return Integer.parseInt(userNumber);
            }
        }
    }
    //************** End of Initialization Methods ************\\

    //#####################          GAME CODE          ###################\\
    //#####################  HEAVY LIFTING IS DONE HERE  ##################\\


    // Initializes the real game. This is where the bulk of the action happens. //
    public void initializeGame() {
        this.mainWorld.generateWorld(70, 20);
        TETile[][] worldFrame = mainWorld.world;

        Player p = new Player(this);
        p.insertPlayer(mainWorld);
        this.mainWorld.player = p;

        new Warp(mainWorld, "w1");
        new Warp(mainWorld, "w2", HEIGHT / 2);

        for (int i = 0; i < this.mainWorld.coins.length; i++) {
            this.mainWorld.coins[i] = new Coin();
            this.mainWorld.coins[i].insertCoin(mainWorld);
        }

        for (int i = 0; i < this.mainWorld.monsters.length; i++) {
            this.mainWorld.monsters[i] = new Monster(mainWorld.random.nextInt(5), this);
            this.mainWorld.monsters[i].insertMonster(mainWorld);
        }
    }

    public void startGame() {
        initializeGame();
        gameLoop();
    }

    void gameLoop() {
        ter.initialize(WIDTH, HEIGHT + 4);
        TETile[][] worldFrame = this.mainWorld.world;
        ter.renderFrame(worldFrame);
        String tileUnderMouse = "";
        while (!gameOver) {
            String tileType = readMouse4Tile(mainWorld);
            if (!tileUnderMouse.equals(tileType)) {
                tileUnderMouse = tileType;
                StdDraw.clear(Color.BLACK);
                ter.renderFrame(worldFrame);
                hudUpdate(tileType, this.mainWorld.player.coins);
            }
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            controller(key);
            if (colon > 0) {
                colon -= 1;
            }
            ter.renderFrame(worldFrame);
            hudUpdate(tileType, this.mainWorld.player.coins); //
            checkWinConditions();
            continue;
        }
        drawGameOverFrame();
        StdDraw.pause(5000);
        System.exit(0);
    }

    // Function to check if any win conditions have been met //
    // Only one has been identified so far, but we can edit this method if we think of more//
    void checkWinConditions() {
        if (this.mainWorld.coins.length == this.mainWorld.player.coins) {
            drawWinFrame();
            StdDraw.pause(10000);
            System.exit(2);
        }
    }

    // Forces monster to move randomly in one tile //
    void randomMove(Monster x) {
        int initial = x.xPos + x.yPos;
        int tolerance = 10;
        while (x.xPos + x.yPos == initial && tolerance > 0) {
            int decision = this.mainWorld.random.nextInt(4);
            if (decision == 0) {
                moveMonster(1, 0, x);
            } else if (decision == 1) {
                moveMonster(0, 1, x);
            } else if (decision == 2) {
                moveMonster(-1, 0, x);
            } else if (decision == 3) {
                moveMonster(0, -1, x);
            }
            tolerance -= 1;
        }
    }

    // Controller that processess user input and sends it to the correct method //
    void controller(char key) {
        if (key == 'W' || key == 'w') {
            moveCharacter(0, 1);
            monsterMotion();
        } else if (key == 'S' || key == 's') {
            moveCharacter(0, -1);
            monsterMotion();
        } else if (key == 'D' || key == 'd') {
            moveCharacter(1, 0);
            monsterMotion();
        } else if (key == 'A' || key == 'a') {
            moveCharacter(-1, 0);
            monsterMotion();
        } else if (key == 'O' || key == 'o') {
            warp();
        } else if (key == 'Q' || key == 'q') {
            if (colon > 0) {
                saveGame(this);
                gameOver = true;
            }
        } else if (key == ':') {
            colon += 2;
        }

    }

    void monsterMotion() {
        for (Monster x : mainWorld.monsters) {
            randomMove(x);
        }
    }

    // Moves a monster horz units in the x direction and vert units in the y direction. //
    void moveMonster(int horz, int vert, Monster thing) {
        int projectedX = thing.xPos + horz;
        int projectedY = thing.yPos + vert;

        if (!(this.mainWorld.world[projectedX][projectedY].description().equals("wall"))
                && !(this.mainWorld.world[projectedX][projectedY].
                description().equals("IM GONNA GET U"))) {
            this.mainWorld.world[thing.xPos][thing.yPos] = thing.current;
            thing.xPos += horz;
            thing.yPos += vert;
            thing.current = this.mainWorld.world[projectedX][projectedY];
            thing.interaction(thing.current);
            this.mainWorld.world[projectedX][projectedY] = thing.type;
        }
    }

    // Moves our Player horz units in the x direction and vert units in the y direction. //
    void moveCharacter(int horz, int vert) {
        int projectedX = this.mainWorld.player.xPos + horz;
        int projectedY = this.mainWorld.player.yPos + vert;

        if (!(this.mainWorld.world[projectedX][projectedY].description().equals("wall"))
                &&
                !(this.mainWorld.world[projectedX][projectedY].description().equals("nothing"))) {
            if (this.mainWorld.player.current.description().equals("coin")) {
                this.mainWorld.world[this.mainWorld.player.xPos][this.mainWorld.player.yPos]
                        = Tileset.FLOOR;
            } else {
                this.mainWorld.world[this.mainWorld.player.xPos][this.mainWorld.player.yPos]
                        = this.mainWorld.player.current;
            }
            this.mainWorld.player.xPos += horz;
            this.mainWorld.player.yPos += vert;
            this.mainWorld.player.current = this.mainWorld.world[projectedX][projectedY];
            this.mainWorld.player.interaction(this.mainWorld.player.current);
            this.mainWorld.world[projectedX][projectedY] = this.mainWorld.player.type;
        }
    }

    void warp() {
        World world = this.mainWorld;
        TETile[][] worldTiles = world.world;
        Player p = world.player;

        if (world.player.current.description().equals("warp")) {

            //Identify which Warp the Player is in
            Warp oldWarp = whichWarpIn(p);
            Warp newWarp = null;

            //Find another Warp
            for (Warp w : world.warps.values()) {
                if (w.equals(oldWarp)) {
                    continue;
                }
                newWarp = w;
            }

            mainWorld.warpFlash();
            ter.renderFrame(this.mainWorld.world);

            StdDraw.pause(400);

            this.mainWorld.warpUnflash();

            //Spawn player in bottom left of the new Warp
            worldTiles[newWarp.xStart][newWarp.yStart] = p.type;
            p.xPos = newWarp.xStart;
            p.yPos = newWarp.yStart;

            ter.renderFrame(this.mainWorld.world);

        } //do nothing if Player not in Warp
        return;
    }

    /**
     * returns which Warp player is in
     */
    Warp whichWarpIn(Player p) {
        int pX = p.xPos;
        int pY = p.yPos;

        for (Warp w : mainWorld.warps.values()) {
            if (pX >= w.xStart && pX <= w.xEnd
                    && pY >= w.yStart && pY <= w.yEnd) {
                return w;
            }
        }
        return null; //Player's not in a warp
    }


    //###################   END OF MAIN GAME CODE   ###################\\
    //##################################################################\\

    /************************ HUD **********************/

    public void hudUpdate(String type, int coins) {

        StdDraw.setPenColor(Color.white);
        StdDraw.line(0, HEIGHT + 0.25, WIDTH, HEIGHT + 0.25);

        Font font = new Font("Calibri", Font.ITALIC, 22);
        StdDraw.setFont(font);

        StdDraw.text(midWidth, HEIGHT + 2, "The M&M Game");

        font = new Font("Monaco", Font.PLAIN, 14);
        StdDraw.setFont(font);
        StdDraw.textLeft(1, HEIGHT + 1, "Coins: " + coins);

        StdDraw.textRight(WIDTH - 4, HEIGHT + 1, type);

        //Set back to original so TETile characters don't change
        font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);

        StdDraw.show();
    }

    /**
     * returns Tile Type under mouse for given World
     */
    public static String readMouse4Tile(World myWorld) {
        int x = (int) StdDraw.mouseX(); //Casting rounds down
        int y = (int) StdDraw.mouseY();
        if (y < HEIGHT) {
            String type = myWorld.world[x][y].description();
            return type;
        } else {
            return "None";
        }
    }

    /***************************************************/

    /************************ Save and Load **********************/
    private static void saveGame(Game g) {
        File f = new File("./game.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(g);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private static Game loadGame() {
        File f = new File("./game.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                Game loadGame = (Game) os.readObject();
                os.close();
                return loadGame;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no World has been saved yet, we return a new one. */
        return new Game();
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        this.startScreen();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] playWithInputString(String input) {
        // Run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        if (input.charAt(0) == 'N' || input.charAt(0) == 'n') {
            long seedi = Long.parseLong(Character.toString(input.charAt(1)));
            int i = 2;
            while (input.charAt(i) != 's') {
                seedi = 10 * seedi + Long.parseLong(Character.toString(input.charAt(i)));
                i++;
            }
            i++;
            this.seed = (int) seedi;
            this.mainWorld = new World(WIDTH, this.seed);
            this.initializeGame();
            System.out.println(this.seed);
            TETile[][] worldFrame = this.mainWorld.world;
            for (int n = i; n < input.length(); n++) {
                char key = input.charAt(n);
                this.controller(key);
            }
            return worldFrame;
        } else if (input.charAt(0) == 'L' || input.charAt(0) == 'l') {
            Game loadGame = loadGame();
            int i = 1;
            for (int n = i; n < input.length(); n++) {
                char key = input.charAt(n);
                loadGame.controller(key);
            }
            TETile[][] worldFrame = loadGame.mainWorld.world;
            return worldFrame;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {

    }
}
