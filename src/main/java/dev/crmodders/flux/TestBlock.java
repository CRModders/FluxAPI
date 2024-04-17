package dev.crmodders.flux;

import com.badlogic.gdx.graphics.Color;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockEventGenerator;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.generators.BlockModelGenerator;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.Player;
import finalforeach.cosmicreach.world.Zone;

import java.util.List;

public class TestBlock implements IModBlock {

    @Override
    public void onPlace(Zone zone, Player player, BlockState blockState, BlockPosition position) {
        IModBlock.super.onPlace(zone, player, blockState, position);
        System.out.println("onPlace");
    }

    @Override
    public void onBreak(Zone zone, Player player, BlockState blockState, BlockPosition position) {
        IModBlock.super.onBreak(zone, player, blockState, position);
        System.out.println("onBreak");
    }

    @Override
    public void onInteract(Zone zone, Player player, BlockState blockState, BlockPosition position) {
        System.out.println("onInteract");
    }

    @Override
    public BlockGenerator getBlockGenerator() {
        BlockGenerator generator = new BlockGenerator(Identifier.fromString("fluxapi:test_block"), "test_block");
        generator.createBlockState("default", "test_model", true);
        return generator;
    }

    @Override
    public List<BlockModelGenerator> getBlockModelGenerators(Identifier blockId) {
        BlockModelGenerator generator = new BlockModelGenerator(blockId, "test_model");
        generator.createColoredCuboid(0, 0, 0, 16, 16, 16, "green", Color.GREEN);
        return List.of(generator);
    }

    @Override
    public List<BlockEventGenerator> getBlockEventGenerators(Identifier blockId) {
        return List.of();
    }
}