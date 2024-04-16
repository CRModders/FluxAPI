package dev.crmodders.flux.api.block;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.OrderedMap;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.blocks.Block;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataModBlock implements IModBlock {

    public static class JsonBlock {
        public String stringId;
        public LinkedHashMap<String, String> defaultParams;
        public LinkedHashMap<String, BlockGenerator.State> blockStates;
    }

    public String blockName;

    public DataModBlock(String blockName) {
        this.blockName = blockName;
    }

    @Override
    public BlockGenerator getBlockGenerator() {
        Json json = new Json();
        String blockJson = GameAssetLoader.loadAsset("blocks/"+blockName+".json").readString();
        JsonBlock block = json.fromJson(JsonBlock.class, blockJson);
        BlockGenerator generator = new BlockGenerator(Identifier.fromString(block.stringId), blockName);
        generator.defaultParams = block.defaultParams;
        generator.blockStates = block.blockStates;
        return generator;
    }
}
