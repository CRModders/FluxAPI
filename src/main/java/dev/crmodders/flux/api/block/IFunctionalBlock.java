package dev.crmodders.flux.api.block;

import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.Player;
import finalforeach.cosmicreach.world.Zone;

@Deprecated
public interface IFunctionalBlock extends IModBlock {

    default void onInteract(Zone zone, Player player, BlockState blockState, BlockPosition position) {}
    default void onPlace(Zone zone, Player player, BlockState blockState, BlockPosition position) {}
    default void onBreak(Zone zone, Player player, BlockState blockState, BlockPosition position) {}

}
