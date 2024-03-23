package io.github.crmodders.flux.api.registries;

import finalforeach.cosmicreach.world.blockevents.BlockEvents;
import finalforeach.cosmicreach.world.blockevents.IBlockEventAction;
import finalforeach.cosmicreach.world.blocks.Block;
import io.github.crmodders.flux.api.blocks.ModBlock;

public class BuiltInRegistries {

    public static FreezeableRegistry<ModBlock> MODDED_BLOCKS = FreezeableRegistry.create(ModBlock.class);
    public static FreezeableRegistry<IBlockEventAction> MODDED_BLOCK_ACTIONS = FreezeableRegistry.create(IBlockEventAction.class);
    public static FreezeableRegistry<BlockEvents> MODDED_BLOCK_EVENTS = FreezeableRegistry.create(BlockEvents.class);
    public static StaticRegistry<IBlockEventAction> VANILLA_BLOCKS_ACTIONS;
    public static StaticRegistry<Block> VANILLA_BLOCKS;
}
