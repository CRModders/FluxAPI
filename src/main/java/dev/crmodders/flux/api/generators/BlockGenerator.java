package dev.crmodders.flux.api.generators;

import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.api.factories.IGenerator;
import dev.crmodders.flux.loading.block.BlockLoader;
import dev.crmodders.flux.tags.Identifier;

import java.util.HashMap;
import java.util.Map;

public class BlockGenerator implements IGenerator {

    public static class State {
        /**
         * modelName is used for locating the model used for this block
         * for blocks using models in vanilla json files the modelName is
         * a ResourceLocation
         * for blocks using custom models, the modelName is a combination
         * of blockId and modelName
         * this is handled by setting the usingBlockModelGenerator flag in
         * createBlockState
         */
        public String modelName;
        public int lightLevelRed = 0;
        public int lightLevelGreen = 0;
        public int lightLevelBlue = 0;
        public int lightAttenuation = 15;
        /**
         * better name would be eventName
         */
        public String blockEventsId = "base:block_events_default";
        public float blastResistance = 100.0F;
        public boolean generateSlabs = false;
        public boolean catalogHidden = false;
        public boolean isTransparent = false;
        public boolean isOpaque = true;
        public boolean walkThrough = false;
        public boolean cullsSelf = true;
        public boolean itemCatalogHidden = false;
        public boolean canRaycastForBreak = true;
        public boolean canRaycastForPlaceOn = true;
        public boolean canRaycastForReplace = false;
        public boolean isFluid = false;

    }

    public Identifier blockId;
    public String blockName;
    public Map<String, String> defaultParams;
    public Map<String, State> blockStates;

    public BlockGenerator(Identifier blockId, String blockName) {
        this.blockId = blockId;
        this.blockName = blockName;
        this.defaultParams = new HashMap<>();
        this.blockStates = new HashMap<>();
    }

    public State createBlockState(String id, String modelName, boolean usingBlockModelGenerator) {
        State state = new State();
        state.modelName = usingBlockModelGenerator ? BlockModelGenerator.getModelName(blockId, modelName) : modelName;
        state.blockEventsId = BlockEventGenerator.getEventName(blockId, "flux_default");
        blockStates.put(id, state);
        return state;
    }

    public State createBlockState(String id, String modelName, boolean usingBlockModelGenerator, String eventName, boolean usingBlockEventGenerator) {
        State state = new State();
        state.modelName = usingBlockModelGenerator ? BlockModelGenerator.getModelName(blockId, modelName) : modelName;
        state.blockEventsId = usingBlockEventGenerator ? BlockEventGenerator.getEventName(blockId, eventName) : eventName;
        blockStates.put(id, state);
        return state;
    }

    @Override
    public void register(BlockLoader loader) {}

    @Override
    public String generateJson() {
        Json json = new Json();
        json.setTypeName(null);
        return """
                {"stringId":%s,"blockStates":%s}
                """.formatted(blockId.toString(), json.toJson(blockStates));
    }
}
