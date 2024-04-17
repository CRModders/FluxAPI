package dev.crmodders.flux.api.resource.generation.texture.model.direction;

import com.badlogic.gdx.math.Vector3;

import java.util.*;
import java.util.stream.Stream;

/**
 * Defines a map of {@link Direction|Directions}.
 */
public class DirectionMap {
    public Map<String, Direction> directions;

    /**
     * Initializes a new DirectionMap.
     * @param {Array<Direction>} [directions=[]] The {@link Direction|Directions} to initialize the map with.
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
        List<DirectionList> combinations = new ArrayList<>(List.of(new DirectionList[0]));

        for(int i = 0; i < totalCombinations; i++) {
            String bits = padLeftZeros(Integer.toBinaryString(i), keys.length);
            int finalI = i;
            Stream<Direction> combination = Arrays.stream(keys).filter((i1) -> String.valueOf(bits.getBytes()[finalI]).equals("1")).map(k -> this.directions.get(k));
            combinations.add(new DirectionList(combination.toList()));
        }

        return combinations.toArray(new DirectionList[0]);
    }
}
