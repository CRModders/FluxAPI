package dev.crmodders.flux.api.v5;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.annotations.LegacyInternal;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockState;

@LegacyInternal
public class BlockReg {

    public static Block getBlockFromBlock(Identifier id, Block b) {
        Array<String> blockStateKeysToAdd = b.blockStates.keys().toArray();

        BlockState blockState;
        for (String stateKey : blockStateKeysToAdd) {
            blockState = b.blockStates.get(stateKey);
            blockState.initialize(b);
            blockState.stringId = stateKey;

            Block.allBlockStates.put(blockState.stringId, blockState);
        }

        Block.blocksByStringId.put(id.toString(), b);
        Block.blocksByName.put(id.name, b);
        return b;
    }

    public static Block getBlockFromJson(Identifier id, String str) {
        Json json = new Json();
        Block b = json.fromJson(Block.class, str);
        Array<String> blockStateKeysToAdd = b.blockStates.keys().toArray();

        BlockState blockState;
        for (String stateKey : blockStateKeysToAdd) {
            blockState = b.blockStates.get(stateKey);
            blockState.initialize(b);
            blockState.stringId = stateKey;

            Block.allBlockStates.put(blockState.stringId, blockState);
        }

        Block.blocksByStringId.put(id.toString(), b);
        Block.blocksByName.put(id.name, b);
        return b;
    }

    public static Block getBlockFromJson(Identifier id) {
        Json json = new Json();
        Block b = json.fromJson(Block.class, GameAssetLoader.loadAsset("assets/" + id.namespace + "/blocks/" + id.name + ".json"));
        Array<String> blockStateKeysToAdd = b.blockStates.keys().toArray();

        BlockState blockState;
        for (String stateKey : blockStateKeysToAdd) {
            blockState = b.blockStates.get(stateKey);
            blockState.initialize(b);
            blockState.stringId = stateKey;

//            if (blockState.generateSlabs) generateSlabs(blockState.stringId, blockState);

            Block.allBlockStates.put(blockState.stringId, blockState);
        }

        Block.blocksByStringId.put(id.toString(), b);
        Block.blocksByName.put(id.name, b);
        return b;
    }

}