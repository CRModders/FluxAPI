package io.github.crmodders.flux.api.registries;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.world.blocks.Block;
import finalforeach.cosmicreach.world.blocks.BlockState;
import io.github.crmodders.flux.FluxAPI;
import io.github.crmodders.flux.api.blocks.ModBlock;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class BlockReg {

    public static void create(CallbackInfo ci) {
        BuiltInRegistries.BLOCKS.freeze();
        for (String blockID : ((BasicRegistry<ModBlock>) BuiltInRegistries.BLOCKS).OBJECTS.keySet().toArray(String[]::new)) {
            ModBlock modBlock = ((BasicRegistry<ModBlock>) BuiltInRegistries.BLOCKS).get(Identifier.fromString(blockID));
            if (modBlock.block != null) {
                Block.blocksByName.put(blockID, getBlockFromBlock(Identifier.fromString(blockID), modBlock.block));
            } else {
                Block.blocksByName.put(blockID, getBlockFromJson(Identifier.fromString(blockID)));
            }
        }
    }

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
