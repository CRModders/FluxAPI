package dev.crmodders.flux.api.v2.blocks;

import dev.crmodders.flux.api.v5.block.IModBlock;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.Player;
import finalforeach.cosmicreach.world.Zone;

@SuppressWarnings("removal")
public interface WorkingBlock extends IModBlock {

    void onInteract(Zone zone, Player player, BlockState blockState, BlockPosition position);
    void onPlace(Zone zone, Player player, BlockState blockState, BlockPosition position);
    void onBreak(Zone zone, Player player, BlockState blockState, BlockPosition position);

}