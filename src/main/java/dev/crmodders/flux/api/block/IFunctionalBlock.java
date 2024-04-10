package dev.crmodders.flux.api.block;

import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.Player;
import finalforeach.cosmicreach.world.Zone;

/**
 * Adds an Interactive block to Cosmic Reach
 * @deprecated
 * This class has been replaced by {@link IModBlock}
 */
@Deprecated
public interface IFunctionalBlock extends IModBlock {

    /**
     * Triggered when a Player Interacts with a block
     * @param zone The Zone that this block is in
     * @param player The Player that interacted with this block
     * @param blockState The Blocks State
     * @param position The Blocks Position
     */
    default void onInteract(Zone zone, Player player, BlockState blockState, BlockPosition position) {}

    /**
     * Triggered when a Player Places this block
     * @param zone The Zone that this block is in
     * @param player The Player that interacted with this block
     * @param blockState The Blocks State
     * @param position The Blocks Position
     */
    default void onPlace(Zone zone, Player player, BlockState blockState, BlockPosition position) {}

    /**
     * Triggered when a Player Breaks this block
     * @param zone The Zone that this block is in
     * @param player The Player that interacted with this block
     * @param blockState The Blocks State
     * @param position The Blocks Position
     */
    default void onBreak(Zone zone, Player player, BlockState blockState, BlockPosition position) {}

}
