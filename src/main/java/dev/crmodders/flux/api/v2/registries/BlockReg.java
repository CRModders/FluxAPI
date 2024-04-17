package dev.crmodders.flux.api.v2.registries;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.api.v2.blocks.ModBlock;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class BlockReg {

    public static void create(CallbackInfo ci) {
        BuiltInRegistries.MODDED_BLOCK_EVENTS.freeze();
        for (Identifier eventID : BuiltInRegistries.MODDED_BLOCK_EVENTS.access().getRegisteredNames()) {
            BlockEvents event = BuiltInRegistries.MODDED_BLOCK_EVENTS.access().get(eventID);
            BlockEvents.INSTANCES.put(eventID.toString(), event);
        }

        BuiltInRegistries.MODDED_BLOCKS.freeze();
        for (Identifier blockID : BuiltInRegistries.MODDED_BLOCKS.access().getRegisteredNames()) {
            ModBlock modBlock = BuiltInRegistries.MODDED_BLOCKS.access().get(blockID);
            if (modBlock.block != null) {
                Block.blocksByName.put(blockID.toString(), getBlockFromBlock(blockID, modBlock.block));
            } else {
                Block.blocksByName.put(blockID.toString(), getBlockFromJson(blockID));
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