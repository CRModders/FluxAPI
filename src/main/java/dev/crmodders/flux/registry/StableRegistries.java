package dev.crmodders.flux.registry;

import finalforeach.cosmicreach.world.blockevents.IBlockEventAction;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventData;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.registry.registries.FreezingRegistry;

public class StableRegistries {

    public static FreezingRegistry<IModBlock> BLOCKS = FreezingRegistry.create();
    public static FreezingRegistry<BlockEventDataExt> BLOCK_EVENTS = FreezingRegistry.create();
    public static FreezingRegistry<IBlockEventAction> BLOCK_EVENT_ACTIONS = FreezingRegistry.create();

}
