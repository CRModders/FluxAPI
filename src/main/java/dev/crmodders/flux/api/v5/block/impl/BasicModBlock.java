package dev.crmodders.flux.api.v5.block.impl;

import dev.crmodders.flux.api.v5.block.IModBlock;
import dev.crmodders.flux.api.v5.generators.BlockGenerator;

/**
 * A Basic {@link IModBlock} implementation that utilizes
 * the builtin <a href = "https://cosmicreach.wiki.gg/wiki/Modding/Assets">Data Modding</a>
 * features depending on your registered block ID
 */
public class BasicModBlock implements IModBlock {

    BlockGenerator generator;

    public BasicModBlock() {
        generator = BlockGenerator.createGenerator();
    }

    @Override
    public BlockGenerator getGenerator() {
        return generator;
    }
}
