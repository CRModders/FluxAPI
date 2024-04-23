package dev.crmodders.flux.api.v5.block.impl;

import dev.crmodders.flux.api.v5.block.IModBlock;
import dev.crmodders.flux.api.v5.generators.BlockGenerator;
import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.tags.Identifier;

/**
 * A Basic {@link IModBlock} implementation that utilizes
 * the builtin <a href = "https://cosmicreach.wiki.gg/wiki/Modding/Assets">Data Modding</a> features,
 * and allows you to specify resources from other mods.
 */
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
