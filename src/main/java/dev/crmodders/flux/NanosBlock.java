package dev.crmodders.flux;

import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BasicCubeModelGenerator;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.generators.BlockModelGenerator;
import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.Player;
import finalforeach.cosmicreach.world.Zone;

import java.util.List;

public class NanosBlock implements IModBlock {

    public static final Identifier BLOCK_ID = new Identifier("machines", "smelter");
    public static final String BLOCK_NAME = "auto_smelter";
    public static final ResourceLocation TEXTURE_SIDE = new ResourceLocation(FluxConstants.MOD_ID, "textures/blocks/flux_furnace_side.png");
    public static final ResourceLocation TEXTURE_FRONT_ON = new ResourceLocation(FluxConstants.MOD_ID, "textures/blocks/flux_furnace_front_on.png");
    public static final ResourceLocation TEXTURE_FRONT_OFF = new ResourceLocation(FluxConstants.MOD_ID, "textures/blocks/flux_furnace_front_off.png");

    @Override
    public void onPlace(Zone zone, Player player, BlockState blockState, BlockPosition position) {
        IModBlock.super.onPlace(zone, player, blockState, position);
    }

    @Override
    public void onBreak(Zone zone, Player player, BlockState blockState, BlockPosition position) {
        IModBlock.super.onBreak(zone, player, blockState, position);
    }

    @Override
    public void onInteract(Zone zone, Player player, BlockState blockState, BlockPosition position) {
        // maybe open a UI here ?
    }

    @Override
    public BlockGenerator getBlockGenerator() {
        BlockGenerator generator = new BlockGenerator(BLOCK_ID, BLOCK_NAME);
        BlockGenerator.State on = generator.createBlockState("on", "on", true);
        on.lightLevelRed = on.lightLevelGreen = on.lightLevelBlue = 15;
        BlockGenerator.State off = generator.createBlockState("off", "off", true);
        off.lightLevelRed = off.lightLevelGreen = off.lightLevelBlue = 0;
        return generator;
    }

    @Override
    public List<BlockModelGenerator> getBlockModelGenerators(Identifier blockId) {
        BlockModelGenerator on = new BasicCubeModelGenerator(blockId, "on", false, TEXTURE_SIDE, TEXTURE_FRONT_ON);
        BlockModelGenerator off = new BasicCubeModelGenerator(blockId, "off", false, TEXTURE_SIDE, TEXTURE_FRONT_OFF);
        return List.of(on, off);
    }

}