package dev.crmodders.flux.api.v5.generators.suppliers;

import dev.crmodders.flux.api.v5.block.IModBlock;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.Player;
import finalforeach.cosmicreach.world.Zone;

@FunctionalInterface
public interface BasicTriggerSupplier {

    /**
     * This is a base for adding triggers to Cosmic Reach and is used in IModBlock
     * @param zone The Zone that this block is in
     * @param player The Player that interacted with this block
     * @param blockState The Blocks State
     * @param position The Blocks Position
     *
     * @see IModBlock
     */
    void runTrigger(Zone zone, Player player, BlockState blockState, BlockPosition position);

}
