package dev.crmodders.flux.api.v2.blocks;

import dev.crmodders.flux.api.v5.block.IFunctionalBlock;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.Player;
import finalforeach.cosmicreach.world.Zone;

@SuppressWarnings("removal")
public interface WorkingBlock extends IFunctionalBlock {

    void onInteract(Zone zone, Player player, BlockState blockState, BlockPosition position);
    void onPlace(Zone zone, Player player, BlockState blockState, BlockPosition position);
    void onBreak(Zone zone, Player player, BlockState blockState, BlockPosition position);

}