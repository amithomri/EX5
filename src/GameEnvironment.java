// amit homri 211321823

import java.util.ArrayList;

/**
 *  a collection of all COLLADABLES objects in a game.
 */
public class GameEnvironment {
    private final ArrayList<Collidable> collidables;
    /**
     * Constructor of gameEnvironment class.

     * @param collidables The given collection of  Collidables.
     */
    public GameEnvironment(ArrayList<Collidable> collidables) {
        this.collidables = new ArrayList<>(collidables);
    }

    /**
     * Adds this object to colladbles if it is not null.
     * @param c colladble to add to collection
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * remove block from game after hit
     * @param c colladble to remove from game
     */
    public void removeCollidable(Collidable c) {

        collidables.remove(c);
    }


    /**
     *  closest collision info for a given trajectory of an Object.
     * @param trajectory the line representing the trajectory of the Object.
     * @return a  CollisionInfo object
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // if empty no collision
        if (collidables.isEmpty()) {
            return null;
        }
        Point start = trajectory.start();
        double minDist = Math.max(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
        Point closestColPoint = null;
        Collidable closestObject = null;
        // Storing the current Collidable's info
        Rectangle currRect;
        Point currPoint;
        for (Collidable currC : collidables) {
            currRect = currC.getCollisionRectangle();
            currPoint = trajectory.closestIntersectionToStartOfLine(currRect);
            // If found a new minimal distance collision
            if (currPoint != null && Util.isGreat(minDist, currPoint.distance(start))) {
                closestObject = currC;
                closestColPoint = currPoint;
                minDist = currPoint.distance(start);
            }
        }
        // if null no collision else return clostest collision info
        return closestObject == null
               ? null : new CollisionInfo(closestColPoint, closestObject);
    }




}
