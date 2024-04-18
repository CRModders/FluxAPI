package dev.crmodders.flux.api.v6.resource.generation.texture.color;

import java.util.HashMap;
import java.util.Map; /**
 * Defines a list of colors.
 */
public class ColorList {
    Map<String, Color> colors;

    /**
     * Initializes a new ColorList.
     * @param colors {Map<string, Color>}
     */
    public ColorList(Color[] colors) {
        this.colors = new HashMap<>();

        for (Color color : colors) {
            this.addColor(color);
        }
    }

//    /** @yields {Color} */
//    *[Symbol.iterator]() {
//        yield* this.colors.values();
//    }

    /**
     * Returns the keys of the color list.
     *
     * @return {Array<string>}
     */
    public String[] keys() {
        return this.colors.keySet().toArray(String[]::new);
    }

    /**
     * Returns the values of the color list.
     *
     * @return {Array<Color>}
     */
    public Color[] values() {
        return this.colors.values().toArray(Color[]::new);
    }

    /**
     * Returns the color with the specified name.
     *
     * @param name {string}
     * @return {Color|undefined}
     */
    public Color getColor(String name) {
        return this.colors.get(name.toLowerCase());
    }

    /**
     * Returns the color at the specified index.
     *
     * @param index {number} The index of the color.
     * @return {Color}
     */
    public Color getColorAtIndex(int index) {
        return this.colors.values().toArray(Color[]::new)[index];
    }

    /**
     * Adds a color to the list.
     *
     * @param colors {...Color} The colors to add.
     */
    public void addColor(Color ...colors) {
        for (Color color : colors) {
            this.colors.put(color.name, color);
        }
    }
}
