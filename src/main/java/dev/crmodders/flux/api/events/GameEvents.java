package dev.crmodders.flux.api.events;

import dev.crmodders.flux.api.events.system.Event;
import dev.crmodders.flux.api.events.system.EventFactory;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.blocks.BlockState;

public class GameEvents {

    // Game Flow Events
    public static final Event<GameEventTriggers.OnGameTickedTrigger> AFTER_GAME_IS_TICKED = EventFactory.createArrayBacked(GameEventTriggers.OnGameTickedTrigger.class, callbacks -> () -> {
        for (GameEventTriggers.OnGameTickedTrigger callback : callbacks) {
            if (callback != null)
                callback.onTick();
        }
    });

    public static final Event<GameEventTriggers.OnGameTickedTrigger> BEFORE_GAME_IS_TICKED = EventFactory.createArrayBacked(GameEventTriggers.OnGameTickedTrigger.class, callbacks -> () -> {
        for (GameEventTriggers.OnGameTickedTrigger callback : callbacks) {
            if (callback != null)
                callback.onTick();
        }
    });


    // Block Events
    public static final Event<GameEventTriggers.OnBlockBrokenTrigger> AFTER_BLOCK_IS_BROKEN = EventFactory.createArrayBacked(GameEventTriggers.OnBlockBrokenTrigger.class, callbacks -> (world, pos, timeSinceLastInteract) -> {
        for (GameEventTriggers.OnBlockBrokenTrigger callback : callbacks) {
            if (callback != null)
                callback.onBlockBroken(world, pos, timeSinceLastInteract);
        }
    });

    public static final Event<GameEventTriggers.OnBlockBrokenTrigger> BEFORE_BLOCK_IS_BROKEN = EventFactory.createArrayBacked(GameEventTriggers.OnBlockBrokenTrigger.class, callbacks -> (world, blockPos, timeSinceLastInteract) -> {
        for (GameEventTriggers.OnBlockBrokenTrigger callback : callbacks) {
            if (callback != null)
                callback.onBlockBroken(world, blockPos, timeSinceLastInteract);
        }
    });

    public static final Event<GameEventTriggers.OnBlockPlacedTrigger> AFTER_BLOCK_IS_PLACED = EventFactory.createArrayBacked(GameEventTriggers.OnBlockPlacedTrigger.class, callbacks -> (world, targetBlockState, blockPos, timeSinceLastInteract) -> {
        for (GameEventTriggers.OnBlockPlacedTrigger callback : callbacks) {
            if (callback != null)
                callback.onBlockPlaced(world, targetBlockState, blockPos, timeSinceLastInteract);
        }
    });

    public static final Event<GameEventTriggers.OnBlockPlacedTrigger> BEFORE_BLOCK_IS_PLACED = EventFactory.createArrayBacked(GameEventTriggers.OnBlockPlacedTrigger.class, callbacks -> (world, targetBlockState, blockPos, timeSinceLastInteract) -> {
        for (GameEventTriggers.OnBlockPlacedTrigger callback : callbacks) {
            if (callback != null)
                callback.onBlockPlaced(world, targetBlockState, blockPos, timeSinceLastInteract);
        }
    });

    // GameState Events
    public static final Event<GameEventTriggers.StateChangedTrigger> AFTER_GAMESTATE_CHANGED = EventFactory.createArrayBacked(GameEventTriggers.StateChangedTrigger.class, callbacks -> gameState -> {
        for (var callback : callbacks) {
            if (callback != null)
                callback.onStateChanged(gameState);
        }
    });

    public static final Event<GameEventTriggers.StateChangedTrigger> BEFORE_GAMESTATE_CHANGED = EventFactory.createArrayBacked(GameEventTriggers.StateChangedTrigger.class, callbacks -> gameState -> {
        for (var callback : callbacks) {
            if (callback != null)
                callback.onStateChanged(gameState);
        }
    });

    public static class GameEventTriggers {

        // Game Flow Triggers
        @FunctionalInterface
        public interface OnGameTickedTrigger {
            void onTick();
        }

        // Block Triggers
        @FunctionalInterface
        public interface OnBlockBrokenTrigger {
            void onBlockBroken(World world, BlockPosition blockPos, double timeSinceLastInteract);
        }

        @FunctionalInterface
        public interface OnBlockPlacedTrigger {
            void onBlockPlaced(World world, BlockState targetBlockState, BlockPosition blockPos, double timeSinceLastInteract);
        }

        // Game State Triggers
        @FunctionalInterface
        public interface StateChangedTrigger {
            void onStateChanged(GameState gameState);
        }
    }

}
