package io.github.crmodders.flux.api.block;

import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.blocks.BlockState;
import finalforeach.cosmicreach.world.entities.Player;

public interface IFunctionalBlock {

    void onInteract(World world, Player player, BlockState blockState, BlockPosition position);
    void onPlace(World world, Player player, BlockState blockState, BlockPosition position);
    void onBreak(World world, Player player, BlockState blockState, BlockPosition position);

}
