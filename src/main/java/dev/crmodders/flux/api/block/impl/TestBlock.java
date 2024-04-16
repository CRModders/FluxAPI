package dev.crmodders.flux.api.block.impl;

import com.badlogic.gdx.graphics.Color;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.generators.BlockModelGenerator;
import dev.crmodders.flux.tags.Identifier;

import java.util.List;

public class TestBlock implements IModBlock {
        @Override
        public BlockGenerator getBlockGenerator() {
            BlockGenerator generator = new BlockGenerator(Identifier.fromString("fluxapi:test_block"), "test_block");
            BlockGenerator.State default0 = generator.createBlockState("default", "test_model");
            return generator;
        }

        @Override
        public List<BlockModelGenerator> getBlockModelGenerators(Identifier blockId) {
            BlockModelGenerator generator = new BlockModelGenerator(blockId, "test_model");
            generator.createColoredCuboid(0, 0, 0, 16, 16, 16, "green", Color.GREEN);
            return List.of(generator);
        }
    }