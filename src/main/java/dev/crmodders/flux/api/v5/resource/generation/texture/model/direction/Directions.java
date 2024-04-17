package dev.crmodders.flux.api.v5.resource.generation.texture.model.direction;

import java.util.List;

/**
 * Defines a set of predefined {@link Direction|Directions}.
 *
 */
public class Directions {
    /**
     * Capitalizes the first character of a direction name.
     *
     * @static
     * @param {string} direction The direction name to capitalize.
     * @returns {string}
     */
    public static String capitalize(String direction) {
        byte[] bytes = direction.getBytes();
        bytes[0] = String.valueOf(bytes[0]).toUpperCase().getBytes()[0];

        StringBuilder builder = new StringBuilder();
        List.of(bytes).forEach(builder::append);
        return builder.toString();
    }

    /**
     * Uncapitalizes all charactera of a direction name.
     *
     * @static
     * @param {string} direction
     * @returns {string}
     */
    public static String uncapitalize(String direction) {
        return direction.toLowerCase();
    }

    /**
     * The cardinal directions.
     *
     * @static
     */
    public static DirectionMap cardinals = new DirectionMap(new Direction[]{
        new Direction("north", 0, 0, 1),
        new Direction("east", 1, 0, 0),
        new Direction("south", 0, 0, -1),
        new Direction("west", -1, 0, 0),
        new Direction("up", 0, 1, 0),
        new Direction("down", 0, -1, 0)
    });

    /**
     * The relative directions.
     *
     * @static
     */
    public static DirectionMap relative = new DirectionMap(new Direction[]{
            new Direction("front", 0, 0, 1),
            new Direction("right", 1, 0, 0),
            new Direction("back", 0, 0, -1),
            new Direction("left", -1, 0, 0),
            new Direction("top", 0, 1, 0),
            new Direction("bottom", 0, -1, 0)
    });

    /**
     * The directions for a simple block.
     *
     * @static
     */
    public static DirectionMap simpleBlock = new DirectionMap(new Direction[]{
        new Direction("front", 0, 0, 1),
        new Direction("back", 0, 0, -1),
        new Direction("side", 0, 0, 0),
    });
}
