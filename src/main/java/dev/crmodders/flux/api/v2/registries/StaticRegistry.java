package dev.crmodders.flux.api.v2.registries;

import com.google.common.collect.ImmutableMap;
import dev.crmodders.flux.annotations.LegacyInternal;
import dev.crmodders.flux.api.v5.generators.BlockEventGenerator;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;
import finalforeach.cosmicreach.blocks.Block;

@LegacyInternal
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

    public static StaticRegistry<IBlockAction> initBlockActionRegistry() {
        String[] keys = BlockEvents.ALL_ACTIONS.keySet().toArray(String[]::new);

        ImmutableMap.Builder<String, IBlockAction> blockActionMapBuilder = new ImmutableMap.Builder<>();

        for (String key : keys) {
            blockActionMapBuilder.put(Identifier.fromString(key).toString(), BlockEventGenerator.ALL_ACTION_INSTANCES.get(key));
        }
        return new StaticRegistry<>(blockActionMapBuilder.build());
    }

    public T get(Identifier id) {
        return blockMap.get(id.toString());
    }

}