package dev.crmodders.flux.api.block;

import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.blocks.BlockState;
import finalforeach.cosmicreach.world.entities.Player;

public interface IFunctionalBlock {

    default void onInteract(World world, Player player, BlockState blockState, BlockPosition position) {}
    default void onPlace(World world, Player player, BlockState blockState, BlockPosition position) {}
    default void onBreak(World world, Player player, BlockState blockState, BlockPosition position) {}

}
