package dev.crmodders.flux.loading.block;

import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.resource.ResourceLocation;

public record VanillaModBlock(String blockName) implements IModBlock {
    @Override
    public BlockGenerator getGenerator() {
        return BlockGenerator.createResourceDrivenGenerator(new ResourceLocation( "base", blockName));
    }
}
