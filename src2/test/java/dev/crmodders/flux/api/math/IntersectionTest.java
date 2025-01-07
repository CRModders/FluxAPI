package dev.crmodders.flux.api.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class IntersectionTest {
    @Nested
    class LineSegmentAndAabbUnchecked {
        @ParameterizedTest
        @CsvSource({
            "9.0, 4.5, 4.0, 4.0, 5.0, 10.0, 3.5, 4.0, NONE, 0.0, 0.0, 0.0, 0.0",
        })
        void diagonalLineTest(
            final float x1,
            final float y1,
            final float x2,
            final float y2,
            final float l,
            final float r,
            final float u,
            final float d,
            final Intersection.Convex expectedConvex,
            final float expectedEnterX,
            final float expectedEnterY,
            final float expectedExitX,
            final float expectedExitY
        ) {
            final var buffer = new float[4];

            final Intersection.Convex actualConvex;

            Assertions.assertEquals(
                expectedConvex,
                actualConvex = Intersection.lineSegmentAndAabbUnchecked(
                    x1,
                    y1,
                    x2,
                    y2,
                    l,
                    r,
                    u,
                    d,
                    buffer,
                    0
                ),
                "Convex Intersections"
            );

            if (actualConvex.entered()) {
                final float actualEnterX;
                final float actualEnterY;

                Assertions.assertEquals(expectedEnterX, actualEnterX = buffer[0], "Entering Intersection Point (x)");
                Assertions.assertEquals(expectedEnterY, actualEnterY = buffer[1], "Entering Intersection Point (y)");
            }

            if (actualConvex.exited()) {
                final float actualExitX;
                final float actualExitY;

                Assertions.assertEquals(expectedExitX, actualExitX = buffer[2], "Exiting Intersection Point (x)");
                Assertions.assertEquals(expectedExitY, actualExitY = buffer[3], "Exiting Intersection Point (y)");
            }
        }
    }

    @Nested
    class LineSegmentAndAxesUnchecked {
        /**
         * @see <a href="https://www.desmos.com/calculator/hmba0l06yr">Test Data Visualization on Desmos</a>
         */
        @ParameterizedTest
        @CsvSource({
            "9.0, 4.5, 4.0,  4.0, 5.0,  4.0, 10.0, 4.5, 10.0, 4.0, NONE, 0.0, 0.0",
            "1.0, 0.0, 3.0,  1.0, 2.0,  0.0,  3.0, 1.0,  2.0, 1.0,    Y, 2.0, 0.5",
            "8.0, 3.0, 6.0, -1.0, 6.0, -1.0,  8.0, 2.0,  8.0, 2.0,    X, 7.5, 2.0",
            "0.0, 8.0, 2.0,  6.0, 1.0,  5.0,  3.0, 7.0,  1.0, 7.0, BOTH, 1.0, 7.0",
        })
        void diagonalLineTest(
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
            final Intersection.Axes expectedAxes,
            final float expectedX,
            final float expectedY
        ) {
            final var buffer = new float[2];

            final Intersection.Axes actualAxes;

            Assertions.assertEquals(
                expectedAxes,
                actualAxes = Intersection.lineSegmentAndAxesUnchecked(
                    x1,
                    y1,
                    x2,
                    y2,
                    minX,
                    minY,
                    maxX,
                    maxY,
                    a,
                    b,
                    buffer,
                    0
                ),
                "Intersected Axes"
            );

            if (actualAxes == Intersection.Axes.NONE) {
                return;
            }

            final float actualX;
            final float actualY;

            Assertions.assertEquals(expectedX, actualX = buffer[0], "Intersection Point (x)");
            Assertions.assertEquals(expectedY, actualY = buffer[1], "Intersection Point (y)");
        }
    }
}
