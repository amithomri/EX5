// amit homri 211321823

import biuoop.DrawSurface;
import java.awt.Color;
import java.util.Objects;

/**
 * Represents a ball in gui .
 */
public class Ball implements Sprite {
    private Point loc; // ball center
    private final int size; // radius
    private  Color color;
    private final GameEnvironment collidables;
    private Velocity v = new Velocity(0, 0);

    /**
     * Constructor of  a Ball object with the specified center location, radius,
     * and color.
     * @param center The center point of the Ball.
     * @param r The radius of the Ball.
     * @param color The color of the Ball.
     * @param collidables The Collidable objects of the game.
     */
    public Ball(Point center, int r, Color color,
                GameEnvironment collidables) {
        this.size = r;
        this.loc = new Point(center.getX(), center.getY());
        this.color = new Color(color.getRGB());
        this.collidables = collidables;
    }

    /**
     * Returns the x-coordinate of the Ball's location.
     * @return The x-coordinate of the Ball's location.
     */
    public int getX() {
        return (int) Math.round(loc.getX());
    }
    /**
     * Returns the y-coordinate of the Ball's location.
     * @return The y-coordinate of the Ball's location.
     */
    public int getY() {
        return (int) Math.round(loc.getY());
    }

    /**
     * @return ball color
     */
    public Color getColor() {
        return color;
    }
    /**
     * set ball color after hit
     * @param c color of block that ball hit
     */
    public void setColor(Color c) {
        this.color = c;
    }

    /**
     * Returns the velocity of the Ball.
     * @return The velocity of the Ball.
     */
    public Velocity getVelocity() {
        return new Velocity(v.getDX(), v.getDY());
    }
    /**
     * Sets the velocity of the Ball to given  velocity.
     * @param v The new velocity of the Ball.
     */
    public void setVelocity(Velocity v) {
        this.v = new Velocity(v.getDX(), v.getDY());
    }
    /**
     * Returns the trajectory line knowing  velocity of the Ball.
     * @return The trajectory line of the Ball.
     */
    private Line getTrajectory() {
        return new Line(getX(), getY(),
                        getX() + v.getDX(), getY() + v.getDY());
    }

    /**
     * Matches the Ball's coordinates to a specified edge coordinate,
     *by  the ball's trajectory.
     * @param edgeCrd The edge coordinate.
     * @param isVertical Whether the edge is vertical.
     * @return The matched coordinate.
     */
    private double matchCoord(double edgeCrd, boolean isVertical) {
        double dx = getVelocity().getDX();
        double dy = getVelocity().getDY();
        if (dx == 0) {        // Vertical speed
            return getX();
        } else if (dy == 0) { // Horizontal speed
            return getY();
        }
        return isVertical
               ? getY() + (dy / dx) * (edgeCrd - getX())
               : getX() + (dx / dy) * (edgeCrd - getY());
    }

    /**
     * Checks if the Ball is inside a given  Rectangle object.
     * @param rect The  Rectangle object to check.
     * @return  true if the Ball is inside the rectangle else false.
     */
    private boolean isInside(Rectangle rect) {
        return Util.isBetween(rect.getUpperLeft().getX(), loc.getX(),
                              rect.getBottomRight().getX())
               && Util.isBetween(rect.getUpperLeft().getY(), loc.getY(),
                                 rect.getBottomRight().getY());
    }

    /**
     * Time passed so we call ball movement method.
     */
    public void timePassed() {
        moveOneStep();
    }

    /**
     * method responsible for ball movement.
     */
    public void moveOneStep() {
        Line trajectory = getTrajectory();
        CollisionInfo cInfo = collidables.getClosestCollision(trajectory);
        // If no collision happend
        if (cInfo == null) {
            loc = new Point(trajectory.end().getX(), trajectory.end().getY());
            return;
        }
        Point colPoint = cInfo.collisionPoint();
        Collidable colObj = cInfo.collisionObject();
        Line[] colRectEdges = colObj.getCollisionRectangle().getRectLines();
        // Handles edge case when  ball  stuck inside  Paddle as  we do  move ball  upwards
        if (Objects.equals(colObj.collidableType(), "Paddle")
                && isInside(colObj.getCollisionRectangle())) {
            v = new Velocity(v.getDX(), -Math.abs(v.getDY()));
            loc = new Point(getX(), colRectEdges[0].middle().getY() - size);
            return;
        }
        if (colRectEdges[0].isOnLine(colPoint)) {          // Hits top edge
            double edgeY = colRectEdges[0].middle().getY();
            loc = new Point(matchCoord(edgeY, false), edgeY - size);
        } else if (colRectEdges[1].isOnLine(colPoint)) {   // Hits bottom edge
            double edgeY = colRectEdges[1].middle().getY();
            loc = new Point(matchCoord(edgeY, false), edgeY + size);
        } else if (colRectEdges[2].isOnLine(colPoint)) {   // Hits left edge
            double edgeX = colRectEdges[2].middle().getX();
            loc = new Point(edgeX - size, matchCoord(edgeX, true));
        } else if (colRectEdges[3].isOnLine(colPoint)) {   // Hits right edge
            double edgeX = colRectEdges[3].middle().getX();
            loc = new Point(edgeX + size, matchCoord(edgeX, true));
        }
        //so set velocity according to hit
        setVelocity(colObj.hit(this, colPoint, v));
    }

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param d The DrawSurface on which to draw the ball.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.fillCircle(getX(), getY(), size);
        d.setColor(Color.BLACK);
        d.drawCircle(getX(), getY(), size);
    }

    /**
     * Adds the ball to the given game as a sprite.
     * @param g The game to which the ball is added.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
