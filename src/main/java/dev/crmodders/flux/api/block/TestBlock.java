package dev.crmodders.flux.api.block;

import dev.crmodders.flux.api.generators.BlockGenerator;

public class TestBlock implements IModBlock, IFunctionalBlock {

    BlockGenerator generator;

    public TestBlock() {
//        generator.modifyBlockState("default", );
    }

    @Override
    public BlockGenerator getGenerator() {
        return null;
    }
}
