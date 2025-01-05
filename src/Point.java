// amit homri 211321823

/**
 * Represents a point in game and board.
 */
public class Point {
    private final double x;
    private final double y;

    /**
     * Constructs a Point object with the specified coordinates.
     * @param x The x-coordinate of the Point.
     * @param y The y-coordinate of the Point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     *  distance between two points.
     * @param other The other Point.
     * @return The distance between this Point and the other Point.
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2)
                         + Math.pow(this.y - other.getY(), 2));
    }

    /**
     * Returns the x-coordinate of this Point.
     * @return The x-coordinate.
     */
    public double getX() {
        return x;
    }
    /**
     * Returns the y-coordinate of this Point.
     * @return The y-coordinate.
     */
    public double getY() {
        return y;
    }
}
