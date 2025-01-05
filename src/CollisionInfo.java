// amit homri 211321823

/**
 * collision info class.
 */
public class CollisionInfo {
    private final Point collisionPoint;
    private final Collidable collisionObject;
    /**
     * Constructor of   CollisionInfo object.
     * @param collisionPoint The point where  the collision happends.
     * @param collisionObject The  Collidable object
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = new Point(collisionPoint.getX(),
                                        collisionPoint.getY());
        this.collisionObject = collisionObject.cloneCollidable();
    }

        /**
         * Returns the collision point .
         * @return The point at which the collision occurs.
         */
    public Point collisionPoint() {
        return new Point(collisionPoint.getX(), collisionPoint.getY());
    }

    /**
     * Returns the  Collidable object involved in the collision.
     * @return The  Collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return collisionObject.cloneCollidable();
    }
}
