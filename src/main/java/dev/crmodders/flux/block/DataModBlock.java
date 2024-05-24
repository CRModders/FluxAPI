package dev.crmodders.flux.block;

import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.assets.VanillaAssetLocations;
import dev.crmodders.flux.generators.BlockGenerator;
import dev.crmodders.flux.tags.ResourceLocation;
import dev.crmodders.flux.tags.Identifier;

import java.util.LinkedHashMap;

/**
 * This class allows loading regular Json files
 * as IModBlocks
 */
public class DataModBlock implements IModBlock {

    public static class JsonBlock {
        public String stringId;
        public LinkedHashMap<String, String> defaultParams;
        public LinkedHashMap<String, BlockGenerator.State> blockStates;
        public String blockEntityId;
        public LinkedHashMap<String, ?> blockEntityParams;
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
        BlockGenerator generator = new BlockGenerator(Identifier.fromString(block.stringId), blockName);
        generator.blockEntityId = block.blockEntityId;
        generator.blockEntityParams = block.blockEntityParams;
        generator.defaultParams = block.defaultParams;
        generator.blockStates = block.blockStates;
        return generator;
    }
}
