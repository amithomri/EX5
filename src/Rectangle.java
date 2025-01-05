// amit homri 211321823

import java.util.List;
import java.util.ArrayList;

/**
 * a rectangle class.
 */
public class Rectangle {
    private final double width;
    private final double height;
    private final Point upperLeft;

    /**
     * a rectangle constructor.
     * @param upperLeft  upper-left point of  Rectangle.
     * @param width width rectangle
     * @param height height rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.width = width;
        this.height = height;
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
    }

    /**
     * Returns  width of the Rectangle.
     * @return width of the Rectangle.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns  height of the Rectangle.
     * @return  height of the Rectangle.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the upper-left point of the Rectangle.
     * @return The upper-left point.
     */
    public Point getUpperLeft() {
        return new Point(upperLeft.getX(), upperLeft.getY());
    }

    /**
     * Returns the bottom-right point of the Rectangle.
     * @return The bottom-right point.
     */
    public Point getBottomRight() {
        return new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }

    /**
     * Returns the 4 lines edges of the Rectangle.
     * @return The Rectangle's edges.
     */
    public Line[] getRectLines() {
        double leftX   = upperLeft.getX();
        double rightX  = getBottomRight().getX();
        double topY    = upperLeft.getY();
        double bottomY = getBottomRight().getY();
        Line topLine    = new Line(leftX, topY, rightX, topY);
        Line bottomLine = new Line(leftX, bottomY, rightX, bottomY);
        Line leftLine   = new Line(leftX, topY, leftX, bottomY);
        Line rightLine = new Line(rightX, topY, rightX, bottomY);
        return new Line[] {topLine, bottomLine, leftLine, rightLine};
    }

    /**
     * Returns a list of all intersection points between a given line and
     * the Rectangle.
     * @param line The line to check for intersections with the Rectangle.
     * @return A list of all intersection points between the given line and
     * the Rectangle.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intPoints = new ArrayList<>();
        for (Line rectLine : getRectLines()) {
            if (rectLine.intersectionWith(line) != null) { // If intersecting
                intPoints.add(rectLine.intersectionWith(line));
            }
        }
        return intPoints;
    }
}
