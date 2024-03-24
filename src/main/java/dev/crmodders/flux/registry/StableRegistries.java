package io.github.crmodders.flux.registry;

import finalforeach.cosmicreach.world.blockevents.IBlockEventAction;
import io.github.crmodders.flux.api.block.IModBlock;
import io.github.crmodders.flux.api.generators.data.blockevent.BlockEventData;
import io.github.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import io.github.crmodders.flux.registry.registries.FreezingRegistry;

public class StableRegistries {

    public static FreezingRegistry<IModBlock> BLOCKS = FreezingRegistry.create();
    public static FreezingRegistry<BlockEventDataExt> BLOCK_EVENTS = FreezingRegistry.create();
    public static FreezingRegistry<IBlockEventAction> BLOCK_EVENT_ACTIONS = FreezingRegistry.create();

}
