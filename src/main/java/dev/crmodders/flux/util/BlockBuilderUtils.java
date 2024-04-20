package dev.crmodders.flux.util;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.api.v5.block.IModBlock;
import dev.crmodders.flux.api.v5.generators.BlockGenerator;
import dev.crmodders.flux.api.v5.resource.ResourceLocation;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockState;

public class BlockBuilderUtils {
    public static Block getBlockFromBlock(Identifier id, Block b) {
        Array<String> blockStateKeysToAdd = b.blockStates.keys().toArray();
        Array.ArrayIterator var4 = blockStateKeysToAdd.iterator();

        while(var4.hasNext()) {
            String stateKey = (String)var4.next();
            BlockState blockState = b.blockStates.get(stateKey);
            blockState.stringId = stateKey;
            blockState.initialize(b);
            Block.allBlockStates.put(blockState.stringId, blockState);
        }

        Block.blocksByStringId.put(id.toString(), b);
        Block.blocksByName.put(id.name+"_"+id.namespace, b);
        return b;
    }

    public static Block getBlockFromJson(Identifier id, String str) {
        Json json = new Json();
        Block b = json.fromJson(Block.class, str);
        Array<String> blockStateKeysToAdd = b.blockStates.keys().toArray();
        Array.ArrayIterator var4 = blockStateKeysToAdd.iterator();

        while(var4.hasNext()) {
            String stateKey = (String)var4.next();
            BlockState blockState = b.blockStates.get(stateKey);
            blockState.stringId = stateKey;
            blockState.initialize(b);
            Block.allBlockStates.put(blockState.stringId, blockState);
        }

        Block.blocksByStringId.put(id.toString(), b);
        Block.blocksByName.put(id.name+"_"+id.namespace, b);
        return b;
    }

    public static Block getBlockFromJson(Identifier id) {
        FluxRegistries.BLOCKS.register(
                id,
                new IModBlock() {
                    @Override
                    public BlockGenerator getGenerator() {
                        return BlockGenerator.createResourceDrivenGenerator(new ResourceLocation(id.namespace, id.name));
                    }

                }
        );
        return null;
    }

    public static Block getBlockFromJSON(Identifier id) {
        Json json = new Json();
        Block b = json.fromJson(Block.class, GameAssetLoader.loadAsset("assets/" + id.namespace + "/blocks/" + id.name + ".json"));
        Array<String> blockStateKeysToAdd = b.blockStates.keys().toArray();
        Array.ArrayIterator var4 = blockStateKeysToAdd.iterator();

        while(var4.hasNext()) {
            String stateKey = (String)var4.next();
            BlockState blockState = b.blockStates.get(stateKey);
            blockState.stringId = stateKey;
            blockState.initialize(b);
            Block.allBlockStates.put(blockState.stringId, blockState);
        }

        Block.blocksByStringId.put(id.toString(), b);
        Block.blocksByName.put(id.name+"_"+id.namespace, b);
        return b;
    }

}
