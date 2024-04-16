package dev.crmodders.flux.api.block;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.OrderedMap;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.resource.ResourceLocation;
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

    public String blockJson;
    public String blockName;

    public DataModBlock(String blockName) {
        this.blockName = blockName;
        this.blockJson = GameAssetLoader.loadAsset("blocks/"+blockName+".json").readString();
    }

    public DataModBlock(String blockName, ResourceLocation json) {
        this.blockName = blockName;
        this.blockJson = json.load().readString();
    }

    public DataModBlock(String blockName, String blockJson) {
        this.blockName = blockName;
        this.blockJson = blockJson;
    }

    @Override
    public BlockGenerator getBlockGenerator() {
        Json json = new Json();
        JsonBlock block = json.fromJson(JsonBlock.class, blockJson);
        BlockGenerator generator = new BlockGenerator(Identifier.fromString(block.stringId), blockName);
        generator.defaultParams = block.defaultParams;
        generator.blockStates = block.blockStates;
        return generator;
    }
}
