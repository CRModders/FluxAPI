package dev.crmodders.flux.api.resource.generation.texture.model.direction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

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

    public DirectionList(Iterable<Direction> directions) {
        this.directions = new HashSet<>();
        directions.forEach(this.directions::add);
    }

    protected DirectionList(DirectionList directions) {
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
    public void forEach(Consumer<Direction> callback) {
        this.directions.forEach(callback);
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
        Direction[] directions = Arrays.stream(directionMap.values()).toArray(Direction[]::new);

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

        directionMap.directions.values().forEach(direction -> {
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
