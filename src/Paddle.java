// amit homri 211321823

import biuoop.KeyboardSensor;
import java.awt.Color;

/**
 * a paddle class.
 */
public class Paddle implements Sprite, Collidable {
    public static final int PADDLE_SPEED = 10;
    private final KeyboardSensor keyboard;
    private final Block block;

    /**
    * CONSTRUCTOR OF PADDLE CLASS.
     * @param rect The paddle's rectangle.
     * @param color The paddle color .
     * @param keyboard  keyboard sensor.
     */
    public Paddle(Rectangle rect, Color color, KeyboardSensor keyboard) {
        this.block = new Block(rect, color,false);
        this.keyboard = keyboard;
    }

    /**
     * another paddle constructor.
     * @param block The given block object.
     * @param keyboard The given keyboard sensor.
     */
    public Paddle(Block block, KeyboardSensor keyboard) {
        this.block = new Block(block.getCollisionRectangle(), block.getColor(), false);
        this.keyboard = keyboard;
    }

    /**
     * Moves the Paddle based on the given direction left and right.
     * @param direction The direction to move -1 to  left and 1 to right.
     */
    private void move(int direction) {
        Rectangle rect = block.getCollisionRectangle();
        double x = rect.getUpperLeft().getX() + direction * PADDLE_SPEED;
        double width = rect.getWidth();
        // move left if true
        if (direction < 0 && Util.isGreat(Game.BORDER, x + width)) {
            x = Game.SCREEN_WIDTH - Game.BORDER;
            // move right if true
        } else if (direction > 0 && Util.isGreat(x, Game.SCREEN_WIDTH - Game.BORDER)) {
            x = Game.BORDER - width;
        }
        Point newUpperLeft = new Point(x, rect.getUpperLeft().getY());
        block.setCollisionRectangle(new Rectangle(newUpperLeft,
                                                  width, rect.getHeight()));
    }

    /**
     * Moves the Paddle to the left.
     */
    public void moveLeft() {
        move(-1);
    }

    /**
     * Moves the Paddle to the right.
     */
    public void moveRight() {
        move(1);
    }

    /**
     * paddle movement method.
     */
    public void moveOneStep() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * time passed to paddle move operates.
     */
    public void timePassed() {
       moveOneStep();
    }

    /**
     * draw paddle on board.
     * @param d The drawing surface.
     */
    public void drawOn(biuoop.DrawSurface d) {
        block.drawOn(d);
    }

    /**
     * the paddle rectangle.
     * @return the paddle rectangle
     */
    public Rectangle getCollisionRectangle() {
        return block.getCollisionRectangle();
    }

    /**
     * Returns a number from 1 to 5 for zone where the collision.
     *in which zone of paddle
     * @param colPoint The collision point between an object and the Paddle's top .
     * @return a zone number in paddle
     */
    private int getZone(Point colPoint) {
        Rectangle rect = block.getCollisionRectangle();
        double x = rect.getUpperLeft().getX();
        double colX = colPoint.getX();
        double fifthSeg = rect.getWidth() / 5;
        if (!Util.isEqual(rect.getUpperLeft().getY(), colPoint.getY())) {
            return 0;
        } else if (Util.isBetween(x, colX, x + fifthSeg)) {
            return 1;
        } else if (Util.isBetween(x + fifthSeg, colX, x + 2 * fifthSeg)) {
            return 2;
        } else if (Util.isBetween(x + 2 * fifthSeg, colX, x + 3 * fifthSeg)) {
            return 3;
        } else if (Util.isBetween(x + 3 * fifthSeg, colX, x + 4 * fifthSeg)) {
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * method to set ball velocity after collision with paddle.
     * @param collisionPoint The point of collision.
     * @param currentVelocity The velocity of the colliding object.
     * @return new velocity after collision
     */
    public Velocity hit(Ball hitter,Point collisionPoint, Velocity currentVelocity) {
        int zone = getZone(collisionPoint);
        // If collision is between the sides of the Paddle
        if (zone == 0) {
            return block.hit(hitter, collisionPoint, currentVelocity);
        }
        Velocity v = null;
        double speed = currentVelocity.getSpeed();
        switch (zone) {
            case 1:
                v = Velocity.fromAngleAndSpeed(300, speed);
                break;
            case 2:
                v = Velocity.fromAngleAndSpeed(330, speed);
                break;
            case 3:
                v = new Velocity(currentVelocity.getDX(),
                                 -currentVelocity.getDY());
                break;
            case 4:
                v = Velocity.fromAngleAndSpeed(30, speed);
                break;
            case 5:
                v = Velocity.fromAngleAndSpeed(60, speed);
                break;
            default:
                // empty since paddle has 5 zones and we covered all cases
        }
        return v;
    }

    /**
     * @return clone of paddle
     */
    public Collidable cloneCollidable() {
        return new Paddle(block, keyboard);
    }

    /**
     * add paddle to game.
     *  @param g The game  object representing the game.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * return string paddle the type of object.
     * @return type of colladble string paddle
     */
    public String collidableType() {
        return "Paddle";
    }
}
