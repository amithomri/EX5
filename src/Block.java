// amit homri 211321823
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a block for game.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private final Color color;
    private List<HitListener> hitListeners = new ArrayList<>();
    private final boolean isBorderBlock;

    /**
     * Constructor of block.
     *
     * @param rect The given rectangle.
     * @param color The given color.
     */
    public Block(Rectangle rect, Color color, boolean isBorderBlock) {
        this.rect = new Rectangle(rect.getUpperLeft(),
                                  rect.getWidth(), rect.getHeight());
        this.color = new Color(color.getRGB());
        this.isBorderBlock = isBorderBlock;
    }

    /**
     * notify hit for listeners.
     * @param hitter ball that hit this block
     */
    private  void notifyHit(Ball hitter) {
// Make a copy of the hitListeners before iterating over them./
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        System.out.println("hit listener should work " + listeners.size());
// Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * @return collision rectangle
     */
    public Rectangle getCollisionRectangle() {
        return new Rectangle(rect.getUpperLeft(),
                             rect.getWidth(),
                             rect.getHeight());
    }

    /**
     * @param ball game ball
     * @return true if ball and block same color else false
     */
    public boolean ballColorMatch(Ball ball) {
        return ball.getColor().equals(color);
    }

    /**
     * adds listener to listeners of block's list
     * @param hl listener to add to list
     */
    public void addHitListener(HitListener hl) {
        System.out.println( "addint listener");
        this.hitListeners.add(hl);
    }
    /**
     * removes listener to listeners of block's list
     * @param hl listener to remove from list
     */
    public void removeHitListener(HitListener hl) {
       this.hitListeners.remove(hl);
    }

    /**
     * in order to remove block we remove from sprites and colladbles.
     * @param game game object
     */
    public void removeFromGame(Game game){
        game.removeSprite(this);
        game.removeCollidable(this);
    }
    /**
     * Sets the collision shape of the shape.
     * @param rectangle The new collision shape of the Block.
     */
    public void setCollisionRectangle(Rectangle rectangle) {
        this.rect = new Rectangle(rectangle.getUpperLeft(),
                                  rectangle.getWidth(),
                                  rectangle.getHeight());
    }

    /**
     * Returns the block color.
     * @return The Block's color.
     */
    public Color getColor() {
        return new Color(color.getRGB());
    }

    /**
     * return velocity after ball hit block.
     * @param collisionPoint The point of collision.
     * @param currentVelocity The velocity of the colliding object.
     * @return  updated velocity of ball after collision
     */
    public Velocity hit(Ball hitter,Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDX();
        double dy = currentVelocity.getDY();
        double collisionX = collisionPoint.getX();
        double collisionY = collisionPoint.getY();
        if(!isBorderBlock) {
            if (!ballColorMatch(hitter)) {
                this.notifyHit(hitter);
                hitter.setColor(color);
            }
        }
        //set ball color to block color;
        // collision in x axes
        if (Util.isEqual(rect.getUpperLeft().getX(), collisionX)
                || Util.isEqual(rect.getBottomRight().getX(), collisionX)) {
            dx = -dx;
        }
        //collision in y axes
        if (Util.isEqual(rect.getUpperLeft().getY(), collisionY)
                || Util.isEqual(rect.getBottomRight().getY(), collisionY)) {
            dy = -dy;
        }
        return new Velocity(dx, dy);
    }

    /**
     * @return clone of block
     */
    public Collidable cloneCollidable() {
        return new Block(rect, color,isBorderBlock);
    }

    /**
     * empty since block can't move.
     */
    public void timePassed() {  }

    /**
     * draws  block on gui.
     * @param d The drawing surface.
     */
    public void drawOn(DrawSurface d) {
        int x = (int) Math.round(rect.getUpperLeft().getX());
        int y = (int) Math.round(rect.getUpperLeft().getY());
        int width = (int) Math.round(rect.getWidth());
        int height = (int) Math.round(rect.getHeight());
        d.setColor(color);
        d.fillRectangle(x, y, width, height);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, width, height);
    }

    /**
     * add block to game by adding to collections.
     * @param g The game  object representing the game.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * type of object colladble.
     * @return string 'Block'
     */
    public String collidableType() {
        return "Block";
    }
    public boolean isBorderBlock() {
        return isBorderBlock;
    }
}
