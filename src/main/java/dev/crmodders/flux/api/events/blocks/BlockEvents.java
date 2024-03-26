package dev.crmodders.flux.api.events.blocks;

import dev.crmodders.flux.api.events.Event;
import dev.crmodders.flux.api.events.EventFactory;
import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.blocks.BlockState;

public class BlockEvents {
    public static final Event<BlockBreak> AFTER_BLOCK_BREAK = EventFactory.createArrayBacked(BlockBreak.class, callbacks -> (world, pos, timeSinceLastInteract) -> {
        for (BlockBreak callback : callbacks) {
            //null check so game does not crash
            if (callback != null)
                callback.blockBreak(world, pos, timeSinceLastInteract);
        }
    });

    public static final Event<BlockBreak> BEFORE_BLOCK_BREAK = EventFactory.createArrayBacked(BlockBreak.class, callbacks -> (world, blockPos, timeSinceLastInteract) -> {
        for (BlockBreak callback : callbacks) {
            //null check so game does not crash
            if (callback != null)
                callback.blockBreak(world, blockPos, timeSinceLastInteract);
        }
    });

    public static final Event<BlockPlace> AFTER_BLOCK_PLACE = EventFactory.createArrayBacked(BlockPlace.class,callbacks -> (world, targetBlockState, blockPos, timeSinceLastInteract) -> {
        for (BlockPlace callback : callbacks) {
            //null check so game does not crash
            if (callback != null)
                callback.blockPlace(world, targetBlockState, blockPos, timeSinceLastInteract);
        }
    });

    public static final Event<BlockPlace> BEFORE_BLOCK_PLACE = EventFactory.createArrayBacked(BlockPlace.class,callbacks -> (world, targetBlockState, blockPos, timeSinceLastInteract) -> {
        for (BlockPlace callback : callbacks) {
            //null check so game does not crash
            if (callback != null)
                callback.blockPlace(world, targetBlockState, blockPos, timeSinceLastInteract);
        }
    });

    @FunctionalInterface
    public interface BlockBreak {
        void blockBreak(World world, BlockPosition blockPos, double timeSinceLastInteract);
    }

    @FunctionalInterface
    public interface BlockPlace {
        void blockPlace(World world, BlockState targetBlockState, BlockPosition blockPos, double timeSinceLastInteract);
    }
}