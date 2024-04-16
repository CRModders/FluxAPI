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

@ActionId(id = "fluxapi:onPlace")
public class OnPlaceTrigger implements IBlockAction {
    @Override
    public void act(BlockState blockState, BlockEventTrigger blockEventTrigger, Zone zone, Map<String, Object> map) {
        Identifier blockId = Identifier.fromString(String.valueOf(map.get("blockId")));
        IModBlock block = FluxRegistries.BLOCKS.access().get(blockId);
        block.onPlace(zone, InGame.getLocalPlayer(), blockState, (BlockPosition) map.get("blockPos"));
    }
}
