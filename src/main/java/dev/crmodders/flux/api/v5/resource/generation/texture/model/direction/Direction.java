package dev.crmodders.flux.api.v5.resource.generation.texture.model.direction;

import com.badlogic.gdx.math.Vector3;

/**
 * Defines a direction.
 */
public class Direction {
    public DirectionMap directionMap;
    public String name;
    public Vector3 vector;

    /**
     * Initializes a new Direction.
     * @param name {string}
     * @param x {number}
     * @param y {number}
     * @param z {number}
     */
    public Direction(String name, int x, int y, int z) {
        /** @type {DirectionMap} */ this.directionMap = null;
        /** @type {string} */ this.name = name;
        this.vector = new Vector3(x, y, z);
    }

    /**
     * Sets the direction map for this direction.
     *
     * @param directionMap {DirectionMap}
     */
    public void setDirectionMap(DirectionMap directionMap) {
        this.directionMap = directionMap;
    }
    
    /**
     * The x component of the direction.
     */
    public int x() {
        return (int) this.vector.x;
    }
    
    /**
     * The y component of the direction.
     */
    public int y() {
        return (int) this.vector.y;
    }
    
    /**
     * The z component of the direction.
     */
    public int z() {
        return (int) this.vector.z;
    }

    /**
     * The name of the direction with the first letter capitalized.
     */
    public String uppercaseName() {
        return String.valueOf(this.name.chars().toArray()[0]).toUpperCase();
    }

    /**
     * Checks whether a direction name is equal to this direction name.
     *
     * @param name {string} The name to check.
     * @return {boolean}
     */
    public boolean is(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    /**
     * Returns the inverse of this direction.
     *
     * @return {Direction} 
     */
    public Direction inverse() {
        return this.directionMap.inverse(this);
    }
    
    /**
     * Returns the string representation of the direction.
     *
     * @return {string} 
     */
    @Override
    public String toString() {
        return this.name;
    }
}

