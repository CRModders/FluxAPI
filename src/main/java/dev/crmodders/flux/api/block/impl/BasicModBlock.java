package dev.crmodders.flux.api.block.impl;

import com.badlogic.gdx.graphics.Pixmap;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BasicCubeModelGenerator;
import dev.crmodders.flux.api.generators.BlockEventGenerator;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.generators.BlockModelGenerator;
import dev.crmodders.flux.tags.Identifier;

import java.util.Collections;
import java.util.List;

public class BasicModBlock implements IModBlock {

    public Identifier blockId;
    public String blockName;

    public Pixmap top, bottom, side;

    public BasicModBlock(Identifier blockId, String blockName) {
        this.blockId = blockId;
        this.blockName = blockName;
    }

    public void setTextures(Pixmap top, Pixmap bottom, Pixmap side) {
        this.top = top;
        this.bottom = bottom;
        this.side = side;
    }

    @Override
    public BlockGenerator getBlockGenerator() {
        BlockGenerator generator = new BlockGenerator(blockId, blockName);
        generator.createBlockState("default", BlockModelGenerator.getModelName(blockId, "model"));
        return generator;
    }

    @Override
    public List<BlockModelGenerator> getBlockModelGenerators(Identifier blockId) {
        BlockModelGenerator generator = new BasicCubeModelGenerator(blockId, "model", top, bottom, side);
        return List.of(generator);
    }

}
