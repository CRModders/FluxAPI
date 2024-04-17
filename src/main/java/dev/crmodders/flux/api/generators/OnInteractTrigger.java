package dev.crmodders.flux.api.generators;

import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.blockevents.actions.ActionId;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.world.Zone;

import java.util.Map;

@ActionId(id = "fluxapi:mod_block_interact")
public class OnInteractTrigger implements IBlockAction {

    public Identifier blockId;

    @Override
    public void act(BlockState blockState, BlockEventTrigger blockEventTrigger, Zone zone, Map<String, Object> map) {
        IModBlock block = FluxRegistries.BLOCKS.access().get(blockId);
        block.onInteract(zone, InGame.getLocalPlayer(), blockState, (BlockPosition) map.get("blockPos"));
    }
}
