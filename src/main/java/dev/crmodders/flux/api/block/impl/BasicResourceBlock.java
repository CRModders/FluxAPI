package dev.crmodders.flux.api.block.impl;

import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.tags.Identifier;

public class BasicResourceBlock implements IModBlock {

    BlockGenerator generator;

    public BasicResourceBlock(Identifier id) {
        generator = BlockGenerator.createResourceDrivenGenerator(
                new ResourceLocation(
                        id.namespace,
                        id.name
                )
        );
    }

    @Override
    public BlockGenerator getGenerator() {
        return generator;
    }
}
