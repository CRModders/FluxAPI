package dev.crmodders.flux.api.block;

import dev.crmodders.flux.api.generators.BlockGenerator;
import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.blocks.BlockState;
import finalforeach.cosmicreach.world.entities.Player;

public class TestBlock implements IModBlock, IFunctionalBlock {

    BlockGenerator generator;

    public TestBlock() {
        generator = BlockGenerator.createGenerator();
    }

    @Override
    public void onInteract(World world, Player player, BlockState blockState, BlockPosition position) {

    }

    @Override
    public void onPlace(World world, Player player, BlockState blockState, BlockPosition position) {

    }

    @Override
    public void onBreak(World world, Player player, BlockState blockState, BlockPosition position) {

    }

    @Override
    public BlockGenerator getGenerator() {
        return generator;
    }

}
