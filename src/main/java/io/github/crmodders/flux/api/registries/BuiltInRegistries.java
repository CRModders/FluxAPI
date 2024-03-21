package io.github.crmodders.flux.api.registries;

import finalforeach.cosmicreach.world.blocks.Block;
import io.github.crmodders.flux.api.blocks.ModBlock;

public class BuiltInRegistries {

    public static FreezeableRegistry<ModBlock> BLOCKS = FreezeableRegistry.create(ModBlock.class);
}
