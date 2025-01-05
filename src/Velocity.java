// amit homri 211321823

/**
 * velocity class for ball and paddle speed.
 */
public class Velocity {
    private final double dx;
    private final double dy;
    /**
     * consturctor of velocity.
     * @param dx The Velocity component along the x-axis.
     * @param dy The Velocity component along the y-axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Creating a velocity object from an angle and speed.
     * @param angle The angle of the Velocity in degrees.
     * @param speed The speed of the Velocity.
     * @return The Velocity representing the specified angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radian = Math.toRadians(Util.mod(angle - 90, 360));
        double dx = Math.cos(radian) * speed;
        double dy = Math.sin(radian) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Returns velocity on  x axes.
     * @return The velocity on   x axes.
     */
    public double getDX() {
        return dx;
    }

    /**
     * Returns the velocity on y axes.
     *
     * @return The velocity on y axes.
     */
    public double getDY() {
        return dy;
    }

    /**
     * Returns the speed of the Velocity.
     * @return The speed of the Velocity.
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }
}
