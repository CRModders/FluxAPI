package dev.crmodders.flux.api.generation.texture.model.direction;

import com.badlogic.gdx.math.Vector3;

import java.util.*;

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
     * @param {DirectionMap} directionMap
     * @memberof Direction
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
     * @memberof Direction
     */
    @Override
    public String toString() {
        return this.name;
    }
}

/**
 * Defines a list of {@link Direction|Directions}.
 */
public class DirectionList {
    public Set<Direction> directions;

    /**
     * Initializes a new DirectionList.
     * @param directions {Array<Direction>} The {@link Direction|Directions} to initialize the list with.
     */
    public DirectionList(Direction[] directions) {
        this.directions = new HashSet<>();
        this.directions.addAll(Arrays.asList(directions));
    }

    protected DirectionList(DirectionList directions) {
        this.directions = new HashSet<>();
        this.directions = directions.directions;
    }
    
    /**
     * Adds a {@link Direction} to the list.
     *
     * @param direction {Direction} The {@link Direction} to add.
     */
    public void add(Direction direction) {
        this.directions.add(direction);
    }
    
    /**
     * Removes a {@link Direction} from the list.
     *
     * @param direction {Direction} The {@link Direction} to remove.
     */
    public void remove(Direction direction) {
        this.directions.remove(direction);
    }
    
    /**
     * Checks whether a {@link Direction} exists in the list.
     *
     * @param direction {Direction} The {@link Direction} to check.
     */
    public boolean has(Direction direction) {
        return this.directions.contains(direction);
    }

    /**
     * The number of {@link Direction|Directions} in the list.
     */
    public int size() {
        return this.directions.size();
    }

    /** 
     * Executes a provided function once per each {@link Direction} in the list, in insertion order.
     * @param callback {function(Direction, Number, Set)}
     */
    public void forEach(DirectionalForeach callback) {
        this.directions.forEach(callback::get);
    }

    /**
     * Checks whether a direction with the specified name exists in the list.
     *
     * @param name {string} The name of the direction to check.
     * @return {boolean} 
     */
    public boolean hasDirection(String name) {
        for(Direction direction : this.directions) {
            if(direction.is(name)) return true;
        }
        return false;
    }

    /**
     * Removes a direction with the specified name from the list.
     *
     * @param name {string}
     */
    public void removeDirection(String name) {
        this.directions.removeIf(direction -> direction.is(name));
    }

    /**
     * Creates a bitmask from a {@link DirectionMap}.
     *
     * @param directionMap {DirectionMap}
     * @return {number} 
     */
    public int createBitmask(DirectionMap directionMap) {
        int bitmask = 0;
        Direction[] directions = directionMap.values().toArray(Direction[]::new);

        for(int i = 0; i < directions.length; i++) {
            bitmask |= ((this.directions.contains(directions[i]) ? 1 : 0) << i);
        }

        return bitmask;
    }

    /**
     * Inverts a {@link DirectionMap}.
     *
     * @param directionMap {DirectionMap}
     * @return {*} 
     */
    public DirectionList invert(DirectionMap directionMap) throws CloneNotSupportedException {
        DirectionList clone = (DirectionList) this.clone();
        Set<Direction> directions = new HashSet<>();

        directionMap.directions.forEach(direction -> {
            if(!clone.directions.contains(direction)) directions.add(direction);
        });

        clone.directions.clear();
        for(Direction direction : directions) clone.add(direction);

        return clone;
    }

    
    /**
     * Clones the direction list into a new instance.
     *
     * @return {DirectionList} A new instance of the current direction list.
     */
    public DirectionList clone() {
        return new DirectionList(this);
    }

    /**
     * Returns the string representation of the direction list.
     */
    @Override
    public String toString() {
        if(this.directions.isEmpty()) return "none";
        StringBuilder builder = new StringBuilder();
        Iterator<String> strings = this.directions.stream().map(v -> v.name).iterator();
        while (strings.hasNext()) {
            String v = strings.next();
            builder.append(v);
            if (strings.hasNext()) builder.append("-");
        }
        return builder.toString();
    }
}

/**
 * Defines a map of {@link Direction|Directions}.
 */
public class DirectionMap {
    public Map<String, Direction> directions;

    /**
     * Initializes a new DirectionMap.
     * @param {Array<Direction>} [directions=[]] The {@link Direction|Directions} to initialize the map with.
     * @memberof DirectionMap
     */
    public DirectionMap(Direction[] directions) {
        this.directions = new HashMap<>();

        for(Direction direction : directions) this.addDirection(direction);
    }

    /**
     * Adds a {@link Direction} to the map.
     *
     * @param direction {Direction} The {@link Direction} to add.
     */
    public void addDirection(Direction direction) {
        direction.setDirectionMap(this);
        this.directions.put(direction.name, direction);
    }

    /**
     * Gets a {@link Direction} from the map by name.
     *
     * @param name {string} The name of the direction to get.
     * @return {Direction}
     */
    public Direction getDirection(String name) {
        return this.directions.get(name);
    }

    /**
     * Gets the {@link Direction|Directions} within the map.
     *
     * @return {Array<Direction>} 
     */
    public Direction[] values() {
        return this.directions.values().toArray(new Direction[0]);
    }

    /**
     * Gets the direction names within the map.
     *
     * @return {Array<string>}
     */
    public String[] keys() {
        return this.directions.keySet().toArray(new String[0]);
    }

    /**
     * Returns the inverse of a direction.
     * 
     * @param direction {Direction} The direction to invert.
     * @returns {Direction} 
     */
    public Direction inverse(Direction direction) {
        Vector3 inverse = new Vector3().mulAdd(direction.vector, -1);

        return this.vectorToDirection(inverse);
    }

    /**
     * Returns the direction closest to a vector.
     * 
     * @param vector {Vector3} The vector to compare.
     */
    public Direction vectorToDirection(Vector3 vector) {
        float lowestDistance = Integer.MIN_VALUE;
        Direction lowestDistanceDirection = null;

        for(Direction direction : this.directions.values()) {
            Vector3 directionVector = direction.vector;
            Vector3 vector3 = vector;
            float distance = directionVector.nor().dst(vector3.nor());
            
            if(distance < lowestDistance) {
                lowestDistance = distance;
                lowestDistanceDirection = direction;
            }
        }

        return lowestDistanceDirection;
    }

    private String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

    /** 
     * Returns the adjacency list for the direction map.
     * @returns {Array<DirectionList>}
     * https://github.com/arlojay/cosmic-reach-modding-api/blob/main/api/directions.js
     */
    public DirectionList[] combinations() {
        String[] keys = this.directions.keySet().toArray(new String[0]);
        int totalCombinations = (int) Math.pow(keys.length, 2);
        List<DirectionList> combinations = List.of(new DirectionList[0]);

        for(int i = 0; i < totalCombinations; i++) {
            String bits = padLeftZeros(Integer.toBinaryString(i), keys.length);
            DirectionList combination = Arrays.stream(keys).filter((i) -> bits[i] == "1").map(k => this.directions.get(k));
            combinations.push(new DirectionList(combination));
        }

        return combinations.toArray(new DirectionList[0]);
    }
}

/**
 * Defines a set of predefined {@link Direction|Directions}.
 *
 * @class Directions
 */
class Directions {
    /**
     * Capitalizes the first character of a direction name.
     *
     * @static
     * @param {string} direction The direction name to capitalize.
     * @return {string} 
     * @memberof Directions
     */
    static capitalize(Direction direction) {
        return direction[0].toUpperCase() + direction.slice(1);
    }

    /**
     * Uncapitalizes all charactera of a direction name.
     *
     * @static
     * @param {string} direction
     * @return {string} 
     * @memberof Directions
     */
    static uncapitalize(Direction direction) {
        return direction.toLowerCase();
    }

    /**
     * The cardinal directions.
     *
     * @static
     * @memberof Directions
     */
    static cardinals = new DirectionMap([
        new Direction("north", 0, 0, 1),
        new Direction("east", 1, 0, 0),
        new Direction("south", 0, 0, -1),
        new Direction("west", -1, 0, 0),
        new Direction("up", 0, 1, 0),
        new Direction("down", 0, -1, 0)
    ]);

    /**
     * The relative directions.
     *
     * @static
     * @memberof Directions
     */
    static relative = new DirectionMap([
        new Direction("front", 0, 0, 1),
        new Direction("right", 1, 0, 0),
        new Direction("back", 0, 0, -1),
        new Direction("left", -1, 0, 0),
        new Direction("top", 0, 1, 0),
        new Direction("bottom", 0, -1, 0)
    ]);

    /**
     * The directions for a simple block.
     *
     * @static
     * @memberof Directions
     */
    static simpleBlock = new DirectionMap([
        new Direction("front", 0, 0, 1),
        new Direction("back", 0, 0, -1),
        new Direction("side", 0, 0, 0),
    ]);
}
