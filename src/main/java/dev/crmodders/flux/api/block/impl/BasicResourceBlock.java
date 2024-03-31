package dev.crmodders.flux.api.block.impl;

import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventType;
import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.Player;
import finalforeach.cosmicreach.world.Zone;

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

    public void onInteract(Zone zone, Player player, BlockState blockState, BlockPosition position) {}
    public void onPlace(Zone zone, Player player, BlockState blockState, BlockPosition position) {}
    public void onBreak(Zone zone, Player player, BlockState blockState, BlockPosition position) {}

    @Override
    public BlockGenerator getGenerator() {
        return generator;
    }
}
