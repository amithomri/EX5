// amit homri 211321823

/**
 * class for math operations with epsilon for accuracy.
 */
public abstract class Util {
    public static final double EPSILON = 0.00001;

    /**
     * Checks if the first double value is greater than the second.
     * @param a The first double value.
     * @param b The second double value.
     * @return  true if  a var is greater than  b else false.
     */
    public static boolean isGreat(double a, double b) {
        return a - b > EPSILON;
    }

    /**
     * Checks if two double values are equal.
     * @param a The first double value.
     * @param b The second double value.
     * @return  true if a and b are equal else false
     */
    public static boolean isEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON || Double.isNaN(Math.abs(a - b));
    }

    /**
     * Checks if a value is between two other values.
     * @param a The lower boundary.
     * @param b The value to check.
     * @param c The higher boundary.
     * @return  true if  b is between  a and  c else  false .
     */
    public static boolean isBetween(double a, double b, double c) {
        return (isGreat(b, a) || isEqual(b, a))
               && (isGreat(c, b) || isEqual(c, b));
    }

    /**
     * Returns the modulo of two double values.
     * @param a The dividend.
     * @param b The divisor.
     * @return The modulo of {@code a} and {@code b}.
     */
    public static double mod(double a, double b) {
        return isGreat(0, a) ? b + a : a % b;
    }
}
