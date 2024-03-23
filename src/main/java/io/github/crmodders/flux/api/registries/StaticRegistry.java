package io.github.crmodders.flux.api.registries;

import com.google.common.collect.ImmutableMap;
import finalforeach.cosmicreach.world.blockevents.BlockEvents;
import finalforeach.cosmicreach.world.blockevents.IBlockEventAction;
import finalforeach.cosmicreach.world.blocks.Block;

import java.util.Map;

public class StaticRegistry<T> {

    protected ImmutableMap<String, T> blockMap;

    protected StaticRegistry(ImmutableMap<String, T> blockMap) {
        this.blockMap = blockMap;
    }

    public static StaticRegistry<Block> initBlockRegistry() {
        String[] keys = Block.blocksByStringId.keySet().toArray(String[]::new);

        ImmutableMap.Builder<String, Block> blockMapBuilder = new ImmutableMap.Builder<>();

        for (String key : keys) {
            blockMapBuilder.put(Identifier.fromString(key).toString(), Block.blocksByStringId.get(key));
        }
        return new StaticRegistry<>(blockMapBuilder.build());
    }

    public static StaticRegistry<IBlockEventAction> initBlockActionRegistry() {
        String[] keys = BlockEvents.ALL_ACTIONS.keySet().toArray(String[]::new);

        ImmutableMap.Builder<String, IBlockEventAction> blockActionMapBuilder = new ImmutableMap.Builder<>();

        for (String key : keys) {
            blockActionMapBuilder.put(Identifier.fromString(key).toString(), BlockEvents.ALL_ACTIONS.get(key));
        }
        return new StaticRegistry<>(blockActionMapBuilder.build());
    }

    public T get(Identifier id) {
        return blockMap.get(id.toString());
    }

}
