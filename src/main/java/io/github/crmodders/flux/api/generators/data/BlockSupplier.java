package io.github.crmodders.flux.api.generators.data;

import finalforeach.cosmicreach.world.blocks.Block;
import io.github.crmodders.flux.api.blocks.ModBlock;

@FunctionalInterface
public interface BlockSupplier {
    Block getAsBlock(ModBlock block);
}
