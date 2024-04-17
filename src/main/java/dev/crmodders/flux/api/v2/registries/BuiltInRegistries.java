package dev.crmodders.flux.api.v2.registries;

import dev.crmodders.flux.api.v2.blocks.ModBlock;
import dev.crmodders.flux.registry.registries.FreezingRegistry;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;
import finalforeach.cosmicreach.blocks.Block;

public class BuiltInRegistries {

    public static FreezingRegistry<ModBlock> MODDED_BLOCKS = FreezingRegistry.create();
    public static FreezingRegistry<IBlockAction> MODDED_BLOCK_ACTIONS = FreezingRegistry.create();
    public static FreezingRegistry<BlockEvents> MODDED_BLOCK_EVENTS = FreezingRegistry.create();
    public static StaticRegistry<IBlockAction> VANILLA_BLOCKS_ACTIONS;
    public static StaticRegistry<Block> VANILLA_BLOCKS;
}