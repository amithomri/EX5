// amit homri 211321823

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * create our game object.
 */
public class Game {

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final int BORDER = 25;
    private static GUI gui;
    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private  Counter remainingBlocks ;
    private  Counter remainingBalls;
    private BlockRemover removingBlocks ;
    /**
     * our six colors for game rows of blocks.
     */
    private static Color[] colors = {
        (Color.RED),
        (Color.YELLOW),
        (Color.GREEN),
        Color.CYAN,
        Color.MAGENTA, Color.WHITE};

    /**
     * return row color.
     * @param index row number for color
     * @return color for row of blocks
     */
    public static Color getRowColor(int index) {
        return colors[index];
    }

    /**
         * constructor of game.
         */
        public Game() {
            this.sprites = new SpriteCollection(new ArrayList<>());
            this.environment = new GameEnvironment(new ArrayList<>());
            this.remainingBalls = new Counter();
            this.remainingBlocks = new Counter();
            this.removingBlocks = new BlockRemover(this, remainingBlocks);
        }
        /**
         * Adds this Collidable object to the Game's colladbles.
         * @param c The  Collidable object to add.
         */
        public void addCollidable(Collidable c) {
            environment.addCollidable(c);
        }

        public  void removeCollidable(Collidable c) {
            environment.removeCollidable(c);
        }

        /**
         * Adds the given sprite object to the Game collection.
         * @param s The  Sprite object to add.
         */
        public void addSprite(Sprite s) {
            sprites.addSprite(s);
        }
        public void removeSprite(Sprite s) {
            sprites.removeSprite(s);
        }
        /**
         * Generates a  Block object with coordinates,
         * width, height and color block will be removed in future
         * @param x      The x-coordinate of the upper-left point of the Block.
         * @param y      The y-coordinate of the upper-left point of the Block.
         * @param width  The width of the Block's collision rectangle.
         * @param height The height of the Block's collision rectangle.
         * @param color  The color of the Block.
         * @return The generated {@link Block} object.
         */
        private Block genBlockGame(double x, double y, int width, int height,
                               Color color) {
            Rectangle colRect = new Rectangle(new Point(x, y), width, height);
            remainingBlocks.increase(1);
            Block gameBlock = new Block(colRect, color,false);
          //  gameBlock.addHitListener(this.removingBlocks);
            return gameBlock;
        }
    /**
     * Generates a  Block object with coordinates,
     * width, height and color. for border block
     *
     * @param x      The x-coordinate of the upper-left point of the Block.
     * @param y      The y-coordinate of the upper-left point of the Block.
     * @param width  The width of the Block's collision rectangle.
     * @param height The height of the Block's collision rectangle.
     * @param color  The color of the Block.
     * @return The generated {@link Block} object.
     */
    private Block genBlockBorder(double x, double y, int width, int height,
                           Color color) {
        Rectangle colRect = new Rectangle(new Point(x, y), width, height);
        return new Block(colRect, color,true);
    }


        /**
         * Generates a {@link Ball} object based on given point coordinates,
         * radius and color.
         *
         * @param x     The x-coordinate of the center point of the Ball.
         * @param y     The y-coordinate of the center point of the Ball.
         * @param r     The radius of the Ball.
         * @param color The color of the Ball.
         * @return The generated {@link Ball} object.
         */
        private Ball genBall(double x, double y, int r, Color color) {
            Random rnd = new Random();
            Ball b = new Ball(new Point(x, y), r, color, environment);
            b.setVelocity(Velocity.fromAngleAndSpeed(90 + rnd.nextDouble() * 180,
                     4 + rnd.nextDouble() * 2));
            this.remainingBalls.increase(1);
            return b;
        }


    public void createBlocksGames(){
        int width = 50;
        int height = 20;
        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        for (int r = 0; r < 6; r++) { // For a total of 6 rows
             Color color = getRowColor(r);
            for (int c = 12; c > r; c--) { // For a decreasing number of columns
                // genBlockGame(125 + c * width, 100 + r * height,
                // width, height, color,  blockRemover).addToGame(this);
                Block block = genBlockGame(125 + c * width, 100 + r * height, width, height, color);
                block.addHitListener(blockRemover);
                block.addToGame(this);
            }
        }
    }



    /**
         * Initializes a new Game by creating paddle blocks and balls.
         */
        public void initialize() {
            gui = new GUI("", SCREEN_WIDTH, SCREEN_HEIGHT);
            // Adding the background block blue one
            Color color = Color.BLUE;
            genBlockBorder(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, color).addToGame(this);
            // Adding the game blocks for board and collisions
            int width = 50;
            int height = 20;
            BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
            for (int r = 0; r < 6; r++) { // For a total of 6 rows
                color = getRowColor(r);
                for (int c = 12; c > r; c--) { // For a decreasing number of columns
                   // genBlockGame(125 + c * width, 100 + r * height,
                           // width, height, color,  blockRemover).addToGame(this);
                    Block block = genBlockGame(125 + c * width, 100 + r * height, width, height, color);
                    block.addHitListener(blockRemover);
                    block.addToGame(this);
                }
            }
            // Adding two balls for our game
            color = Color.WHITE;
            for (int i = 0; i < 2; i++) {
                genBall(SCREEN_WIDTH / 2.0, SCREEN_HEIGHT / 2.0, 5, color).addToGame(this);
            }
            // generating a paddle for board and game
            width = 70;
            height = 18;
            color = Color.ORANGE;
            Block padBlock = genBlockBorder(SCREEN_WIDTH / 2.0, SCREEN_HEIGHT - BORDER - height,
                    width, height, color);
            KeyboardSensor keyboard = gui.getKeyboardSensor();
            new Paddle(padBlock, keyboard).addToGame(this);
           // Adding the  border blocks for board and collisions
            color = Color.GRAY;
            genBlockBorder(0, 0, SCREEN_WIDTH, BORDER, color).addToGame(this);
            genBlockBorder(0, 0, BORDER, SCREEN_HEIGHT, color).addToGame(this);
            genBlockBorder(0, SCREEN_HEIGHT - BORDER, SCREEN_WIDTH, BORDER, color).addToGame(this);
            genBlockBorder(SCREEN_WIDTH - BORDER, 0, BORDER, SCREEN_HEIGHT, color).addToGame(this);

        }



    /**
         * Runs the Game by starting an animation loop.
         */
        public void run() {
            Sleeper sleeper = new Sleeper();
            int framesPerSecond = 60;
            int millisecondsPerFrame = 1000 / framesPerSecond;
            while (true) {
                DrawSurface d = gui.getDrawSurface();
                sprites.drawAllOn(d);
                gui.show(d);
                sprites.notifyAllTimePassed();
                if (remainingBlocks.getValue() == 0) {
                    gui.close();
                }
                // Timing
                long startTime = System.currentTimeMillis();
                long usedTime = System.currentTimeMillis() - startTime;
                long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
                if (milliSecondLeftToSleep > 0) {
                    sleeper.sleepFor(milliSecondLeftToSleep);
                }
            }
        }
    }

