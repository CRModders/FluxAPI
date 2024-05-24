package dev.crmodders.flux.engine.blocks.actions;

import dev.crmodders.flux.block.IModBlock;
import dev.crmodders.flux.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.blockevents.actions.ActionId;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.world.Zone;

import java.util.Map;

@ActionId(id = "fluxapi:mod_block_place")
public class OnPlaceTrigger implements IBlockAction {

    public Identifier blockId;

    @Override
    public void act(BlockState blockState, BlockEventTrigger blockEventTrigger, Zone zone, Map<String, Object> map) {
        IModBlock block = FluxRegistries.BLOCKS.access().get(blockId);
        block.onPlace(zone, InGame.getLocalPlayer(), blockState, (BlockPosition) map.get("blockPos"));
    }
}
