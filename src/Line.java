// amit homri 211321823

import java.util.List;
/**
 *  a line CLASS.
 */
public class Line {

    private final Point start;
    private final Point end;

    /**
     * Constructs a Line object with the specified start and end points.
     *
     * @param start The starting point of the line.
     * @param end The ending point of the line.
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
    }

    /**
     * Constructs a Line object with  start
     * and end points.
     *
     * @param x1 The x-coordinate of the starting point.
     * @param y1 The y-coordinate of the starting point.
     * @param x2 The x-coordinate of the ending point.
     * @param y2 The y-coordinate of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * returns the middle of the  line.
     * @return A point representing the midpoint of the Line segment.
     */
    public Point middle() {
        double midX = (start.getX() + end.getX()) / 2;
        double midY = (start.getY() + end.getY()) / 2;
        return new Point(midX, midY);
    }

    /**
     * Returns the  start Line.
     * @return A start of line a point
     */
    public Point start() {
        return new Point(start.getX(), start.getY());
    }

    /**
     * Returns end of line.
     * @return end of line a point
     */
    public Point end() {
        return new Point(end.getX(), end.getY());
    }

    /**
     * Checks if  Line is vertical.
     * @return  true if the Line is vertical, else false.
     */
    private boolean isVertical() {
        return  Util.isEqual(start.getX(), end.getX());
    }

    /**
     * return  the slope of  Line.
     * @return The  slope of  Line, or infinity if  Line is vertical.
     */
    private double getSlope() {
        return isVertical()
               ? Double.POSITIVE_INFINITY
               : (end.getY() - start.getY()) / (end.getX() - start.getX());
    }

    /**
     * Calculates the y-intercept of this Line.
     * for ax + b = y so  b = y - ax.
     * @return The y-intercept of the Line.
     */
    private double getYInt() {
        return start.getY() - getSlope() * start.getX();
    }

    /**
     * Checks if a point is in this Line  range.
     * @param p The point to check if in range
     * @return  true if the point is in range, else  false.
     */
    private boolean isBetweenSeg(Point p) {
        return Util.isBetween(Math.min(start.getY(), end.getY()),
                              p.getY(), Math.max(start.getY(), end.getY()))
               && Util.isBetween(Math.min(start.getX(), end.getX()),
                                 p.getX(), Math.max(start.getX(), end.getX()));
    }

    /**
     * Checks if a given point lies on this Line segment.
     * @param p The point to check if on  the line .
     * @return  true if the point lies on this Line else false
     */
    public boolean isOnLine(Point p) {
        if (!isBetweenSeg(p)) {
            return false;
        }
        return isVertical()
               ? Util.isEqual(p.getX(), start.getX())
               : Util.isEqual(getSlope() * p.getX() + getYInt(), p.getY());
    }

    /**
     * returns the intersection point between this Line and another Line.
     * @param other other  Line.
     * @param tSlope The slope of this Line.
     * @param oSlope The slope of the other Line.
     * @return The intersection point of the two Lines.
     */
    private Point getIntPoint(Line other, double tSlope, double oSlope) {
        double x, y;
        if (this.isVertical()) {
            x = this.start.getX();
            y = oSlope * x + other.getYInt();
        } else if (other.isVertical()) {
            x = other.start().getX();
            y = tSlope * x + this.getYInt();
        } else {
            x = (other.getYInt() - this.getYInt()) / (tSlope - oSlope);
            y = tSlope * x + this.getYInt();
        }
        return new Point(x, y);
    }

    /**
     * Checks if this Line intersects with another Line.
     * @param other The other Line.
     * @return  true if the Lines intersect else false
     */
    public boolean isIntersecting(Line other) {
        double tSlope = this.getSlope();
        double oSlope = other.getSlope();
        if (Util.isEqual(tSlope, oSlope)) {
            return isOnLine(other.start()) || isOnLine(other.end());
        }
        Point intPoint = getIntPoint(other, tSlope, oSlope);
        return isOnLine(intPoint) && other.isOnLine(intPoint);
    }

    /**
     * Checks if  Line overlaps with other Line.
     * @param other The other Line.
     * @return true if overlapping lines else false.
     */
    private boolean isOverlapping(Line other) {
        return (isOnLine(other.start()) && other.isOnLine(this.start))
                || (isOnLine(other.start()) && other.isOnLine(this.end))
                || (isOnLine(other.end()) && other.isOnLine(this.start))
                || (isOnLine(other.end()) && other.isOnLine(this.end));
    }

    /**
     * Returns  intersection point with another Line.
     * @param other The other Line.
     * @return The intersection point, or null if the Lines do not intersect
     * or have infinite intersection points.
     */
    public Point intersectionWith(Line other) {
        double tSlope = this.getSlope();
        double oSlope = other.getSlope();
        return isOverlapping(other) || !isIntersecting(other)
               ? null : getIntPoint(other, tSlope, oSlope);
    }

    /**
     * Finds and returns an intersection point between this Line and a given
     * {@link Rectangle} object, whose closest to the Line's starting point.
     *
     * @param rect The given rectangle.
     * @return The closest intersection point to the Line's starting point,
     * or null if no intersections.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intPoints = rect.intersectionPoints(this);
        // if empty no closest intersection
        if (intPoints.isEmpty()) {
            return null;
        }
        Point minDistance = intPoints.get(0);
        // foreach loop to find min distance to start line
        for (Point p : intPoints) {
            if (Util.isGreat(minDistance.distance(start), p.distance(start))) {
                minDistance = p;
            }
        }
        return minDistance;
    }
}
