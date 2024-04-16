package dev.crmodders.flux.api.block.impl;

import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.api.assets.VanillaAssetLocations;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.tags.Identifier;
import java.util.LinkedHashMap;

public class DataModBlock implements IModBlock {

    public static class JsonBlock {
        public Identifier stringId;
        public LinkedHashMap<String, String> defaultParams;
        public LinkedHashMap<String, BlockGenerator.State> blockStates;
    }

    public String blockJson;
    public String blockName;

    public DataModBlock(String blockName) {
        this(blockName, VanillaAssetLocations.getBlock(blockName));
    }

    public DataModBlock(String blockName, ResourceLocation json) {
        this(blockName, json.load().readString());
    }

    public DataModBlock(String blockName, String blockJson) {
        this.blockName = blockName;
        this.blockJson = blockJson;
    }

    @Override
    public BlockGenerator getBlockGenerator() {
        Json json = new Json();
        JsonBlock block = json.fromJson(JsonBlock.class, blockJson);
        BlockGenerator generator = new BlockGenerator(block.stringId, blockName);
        generator.defaultParams = block.defaultParams;
        generator.blockStates = block.blockStates;
        return generator;
    }
}
