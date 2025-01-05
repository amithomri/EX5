// amit homri 211321823
/**
 * An interface for every collidable objects.
 */
public interface Collidable {
    /**
     * Returns the collision shape of the object.
     * @return The rectangle object  shape that collides with.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object of a collision at the given point with the given
     * velocity, and changes the object's velocity per the type of collision.
     *
     * @param collisionPoint The point of collision.
     * @param currentVelocity The velocity of the colliding object.
     * @param hitter ball that hit object colladble
     * @return The new velocity of the hitting object.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * Clones the object for encapsulation purposes.
     *
     * @return The cloned copy of the object.
     */
    Collidable cloneCollidable();

    /**
     * Returns type of Collidable object type name - paddle or block.
     * @return The type of Collidable object.
     */
    String collidableType();
}
