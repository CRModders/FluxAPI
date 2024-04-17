package dev.crmodders.flux;

import dev.crmodders.flux.api.v6.block.FluxBlockAction;
import finalforeach.cosmicreach.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.world.Zone;

import java.util.Map;

public class TestAction extends FluxBlockAction {
    @Override
    public void act(BlockState blockState, BlockEventTrigger blockEventTrigger, Zone zone, Map<String, Object> map) {
        System.out.println(map);
    }
}