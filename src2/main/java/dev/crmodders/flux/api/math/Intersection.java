package dev.crmodders.flux.api.math;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class Intersection {
    public enum Axes {
        NONE, X, Y, BOTH;

        public static final Axes ABSCISSA = X;
        public static final Axes ORDINATE = Y;

        public static final Axes DOMAIN = X;
        public static final Axes RANGE = Y;

        public static final Axes HORIZONTAL = X;
        public static final Axes VERTICAL = Y;

        @Override
        public String toString() {
            return switch (this) {
                case NONE -> "NONE";
                case X -> Axis.X.toString();
                case Y -> Axis.Y.toString();
                default -> "BOTH";
            };
        }
    }

    public enum Axis {
        X, Y;

        public static final Axis ABSCISSA = X;
        public static final Axis ORDINATE = Y;

        public static final Axis DOMAIN = X;
        public static final Axis RANGE = Y;

        public static final Axis HORIZONTAL = X;
        public static final Axis VERTICAL = Y;

        @Override
        public String toString() {
            return switch (this) {
                case X -> "X/ASBCISSA/DOMAIN/HORIZONTAL";
                case Y -> "Y/ORDINATE/RANGE/VERTICAL";
            };
        }
    }

    public enum Convex {
        NONE, ENTER, EXIT, BOTH;

        public int bufferStart() {
            return this == EXIT ? 1 : 0;
        }

        public int bufferSize() {
            return switch (this) {
                case NONE -> 0;
                case ENTER, EXIT -> 1;
                case BOTH -> 2;
            };
        }

        public int componentCount() {
            return 2 * this.bufferSize();
        }

        public int componentStart() {
            return 2 * this.bufferStart();
        }

        public boolean entered() {
            return switch (this) {
                case NONE, EXIT -> false;
                case ENTER, BOTH -> true;
            };
        }

        public boolean exited() {
            return switch (this) {
                case NONE, ENTER -> false;
                case EXIT, BOTH -> true;
            };
        }
    }

    /**
     * Finds line-AABB intersection.
     * <p>
     * This method collects point intersections between a line segment and an AABB,
     * storing into a buffer the point components and sorting it with the distance
     * from the start point; there may be up to two intersections. It is assumed that
     * moving left or up is negative while right or down is positive.
     *
     * <pre>{@code
     *        float[] buffer = new float[4];
     *        int count = intersectLineToAabb(x1, y1, x2, y2, l, r, d, u, buffer);
     *        switch (count) {
     *            case 2:
     *                float x1 = buffer[0];
     *                float y1 = buffer[1];
     *                float x2 = buffer[2];
     *                float y2 = buffer[3];
     *            case 1:
     *                float x = buffer[0];
     *                float y = buffer[1];
     *            case 0:
     *            default:
     *                break;
     *        }
     * }</pre>
     *
     * @param x1  The line's starting point.
     * @param y1  The line's starting point.
     * @param x2  The line's ending point.
     * @param y2  The line's ending point.
     * @param l  The left side of the AABB.
     * @param r  The right side of the AABB.
     * @param d  The top side of the AABB.
     * @param u  The bottom side of the AABB.
     * @param buffer  The output buffer.
     * @return The number of intersections; between zero and two.
     */
    public static Convex lineSegmentAndAabbUnchecked(
        final float x1,
        final float y1,
        final float x2,
        final float y2,
        final float l,
        final float r,
        final float u,
        final float d,
        final float[] buffer,
        final int offset
    ) {
        assert l <= r : "Expected left not greater than right";
        assert u <= d : "Expected up not greater than right";
        assert buffer != null : "Expected nonnull buffer";
        assert offset >= 0 : "Expected non-negative buffer offset";
        assert offset < buffer.length : "Expected offset less than buffer capacity";
        assert buffer.length >= 4 : "Expected buffer capacity no less than four";

        // Java implementation of: https://www.desmos.com/calculator/vhtj9oiwm7

        final float minX;
        final float maxX;
        final float a1;
        final float a2;

        if (x1 <= x2) {
            minX = Math.max(x1, a1 = l);
            maxX = Math.min(x2, a2 = r);
        } else {
            minX = Math.max(x2, a2 = l);
            maxX = Math.min(x1, a1 = r);
        }

        final float minY;
        final float maxY;
        final float b1;
        final float b2;

        if (y1 <= y2) {
            minY = Math.max(y1, b1 = u);
            maxY = Math.min(y2, b2 = d);
        } else {
            minY = Math.max(y2, b2 = u);
            maxY = Math.min(y1, b1 = d);
        }

        final var enter =
            lineSegmentAndAxesUnchecked(x1, y1, x2, y2, minX, minY, maxX, maxY, a1, b1, buffer, offset)
                != Axes.NONE;
        final var exit =
            lineSegmentAndAxesUnchecked(x2, y2, x1, y1, minX, minY, maxX, maxY, a2, b2, buffer, offset + 2)
                != Axes.NONE;

        if (enter && exit) {
            return Convex.BOTH;
        } else if (exit) {
            return Convex.EXIT;
        } else if (enter) {
            return Convex.ENTER;
        } else {
            return Convex.NONE;
        }
    }

    /**
     * Line segment and axes intersection.
     * <p>
     * Collects the point intersection of a line segment and two perpendicular
     * axes, if any. When the line coincides with the
     *
     * @param x1  The line starting x-component.
     * @param y1  The line starting y-component.
     * @param x2  The line ending x-component.
     * @param y2  The line ending y-component.
     * @param minX  The minimum returned x-component.
     * @param minY  The minimum returned y-component.
     * @param maxX  The maximum returned x-component.
     * @param maxY  The maximum returned y-component.
     * @param a  The x-position of the vertical line.
     * @param b  The y-position of the horizontal line.
     * @param buffer  The output buffer.
     * @param offset  The output starting offset.
     * @return The intersection result; amount of intersections.
     */
    public static Axes lineSegmentAndAxesUnchecked(
        final float x1,
        final float y1,
        final float x2,
        final float y2,
        final float minX,
        final float minY,
        final float maxX,
        final float maxY,
        final float a,
        final float b,
        final float[] buffer,
        final int offset
    ) {
        // This is unchecked after all, only present with `-ea` jvm arg
        // Avoiding `&&` here might be more helpful when debugging...
        assert minX <= maxX : "Expected min x no greater than max x";
        assert minY <= maxY : "Expected min y no greater than max y";
        assert buffer != null : "Expected nonnull buffer";
        assert offset >= 0 : "Expected non-negative buffer offset";
        assert offset < buffer.length : "Expected offset less than buffer capacity";
        assert buffer.length >= 2 : "Expected buffer capacity no less than two";

        vertical:
        if (minX <= a && a <= maxX) {
            final float c;

            if (x1 == a && minX <= y1 && y1 <= maxX) {
                c = y1;
            } else if (x1 == x2) {
                break vertical;
            } else {
                c = lineAndVerticalUnchecked(x1, y1, x2, y2, a);
                if (c < minY || c > maxY) {
                    break vertical;
                }
            }

            buffer[offset] = a;
            buffer[offset + 1] = c;

            return c == b ? Axes.BOTH : Axes.VERTICAL;
        }

        horizontal:
        if (minY <= b && b <= maxY) {
            final float c;

            if (y1 == b && minY <= y1 && y1 <= maxY) {
                c = x1;
            } else if (y1 == y2) {
                break horizontal;
            } else {
                c = lineAndHorizontalUnchecked(x1, y1, x2, y2, b);
                if (c < minX || c > maxX) {
                    break horizontal;
                }
            }

            buffer[offset] = c;
            buffer[offset + 1] = b;

            return c == a ? Axes.BOTH : Axes.HORIZONTAL;
        }

        return Axes.NONE;
    }

    /**
     * Line and horizontal intersection.
     *
     * @param x1  Line first point x-component.
     * @param y1  Line first point y-component.
     * @param x2  Line second point x-component.
     * @param y2  Line second point y-component.
     * @param y  The y-position of the horizontal line.
     * @return  The {@code y}-intercept.
     * @implSpec <pre>{@code
     *     Intersection.lineAndAxisUnchecked(x1, x2, y1, y2, y);
     * }</pre>
     * @see #lineAndAxisUnchecked
     */
    public static float lineAndHorizontalUnchecked(
        final float x1,
        final float y1,
        final float x2,
        final float y2,
        final float y
    ) {
        return Intersection.lineAndAxisUnchecked(x1, x2, y1, y2, y);
    }

    /**
     * Line and vertical intersection.
     *
     * @param x1  Line first point x-component.
     * @param y1  Line first point y-component.
     * @param x2  Line second point x-component.
     * @param y2  Line second point y-component.
     * @param x  The x-position of the vertical line.
     * @return  The {@code y}-intercept.
     * @implSpec <pre>{@code
     *     Intersection.lineAndAxisUnchecked(y1, y2, x1, x2, x);
     * }</pre>
     * @see #lineAndAxisUnchecked
     */
    public static float lineAndVerticalUnchecked(
        final float x1,
        final float y1,
        final float x2,
        final float y2,
        final float x
    ) {
        return Intersection.lineAndAxisUnchecked(y1, y2, x1, x2, x);
    }

    /**
     * Line and axis intersection.
     * <p>
     * This method generalizes the computation for the intersection of a line
     * and a vertical or horizontal line. Input validation should be done by the
     * caller with all values are expected to be finite, the output value is
     * not defined otherwise. Additionally, on {@code g1 == g2}, this will
     * return {@code Float.NaN}, and usually means that the given line parallels
     * the axis.
     * <p>
     * For a line segment, check the output, for example,
     * {@code t1 <= t && t <= t2}, given that, {@code t1 <= t2}.
     *
     * @param t1  The line target component start.
     * @param t2  The line target component end.
     * @param g1  The line given component start.
     * @param g2  The line given component end.
     * @param g  The given value of the axis.
     * @return  The value of {@code t} or the intercept to the axis.
     * @implSpec <pre>{@code
     *     (g2 - g1) * (t - t1) == (t2 - t1) * (g - g1)
     *     // Division Property of Equality
     *     t - t1  == (t2 - t1) * (g - g1) / (g2 - g1)
     *     // Subtraction Property of Equality
     *     t == (t2 - t1) * (g - g1) / (g2 - g1) + t1
     * }</pre>
     * @see #lineAndHorizontalUnchecked
     * @see #lineAndVerticalUnchecked
     * @see Float#NaN
     */
    public static float lineAndAxisUnchecked(
        final float t1,
        final float t2,
        final float g1,
        final float g2,
        final float g
    ) {
        return (t2 - t1) * (g - g1) / (g2 - g1) + t1;
    }

    private Intersection() {}
}
