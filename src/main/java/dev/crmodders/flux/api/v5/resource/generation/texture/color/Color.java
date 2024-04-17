package dev.crmodders.flux.api.v5.resource.generation.texture.color;

import org.hjson.JsonObject;

import java.util.HashMap;
import java.util.Map; /**
 * Defines a color.
 *
 * @class Color
 */
public class Color {

    public String name;
    public byte r, g, b;

    /**
     * Initializes a new Color.
     *
     * @param name {string} The name of the color.
     * @param r {byte} The red value of the color.
     * @param g {byte} The green value of the color.
     * @param b {byte} The blue value of the color.
     */

    public Color(String name, int r, int g, int b) {
        this.name = name;
        this.r = (byte) r;
        this.g = (byte) g;
        this.b = (byte) b;
    }

    /**
     * Returns the string representation of the color.
     *
     * @return {string}
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Returns the sRGB representation of the color.
     */
    public JsonObject srgb() {
        JsonObject color = new JsonObject();
        color.set("r", this.r / 15);
        color.set("g", this.g / 15);
        color.set("b", this.b / 15);
        return color;
    }

    /**
     * Returns the sRGB representation of the color in 0-255.
     */
    public JsonObject srgb255() {
        JsonObject color = new JsonObject();
        color.set("r", Math.floor(((double) this.r / 15) * 255));
        color.set("g", Math.floor(((double) this.g / 15) * 255));
        color.set("b", Math.floor(((double) this.b / 15) * 255));
        return color;
    }
}

