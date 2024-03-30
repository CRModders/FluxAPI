package dev.crmodders.flux.api.generators;

import dev.crmodders.flux.api.block.IFunctionalBlock;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventType;
import dev.crmodders.flux.api.generators.data.blockstate.BlockStateData;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import org.hjson.JsonObject;

import java.util.HashMap;

public class BlockStateGenerator {

    protected JsonObject object;

    public static BlockStateGenerator createGenerator() {
        return new BlockStateGenerator();
    }

    public static BlockStateData createBasicBlockState(Identifier blockEventsId, String modelName) {
        return new BlockStateData(
                blockEventsId, modelName
        );
    }

    public static JsonObject ModifiyBlockState(Identifier id, IFunctionalBlock block, JsonObject oldBlockState, HashMap<BlockEventType, Boolean> blockEventOverrideMap) {
        JsonObject blockstate = oldBlockState;
        Identifier blockEventId;
        BlockEventDataExt eventData;
        if (blockstate.get("blockEventsId") == null) {
            blockEventId = Identifier.fromString(id.toString() + "_Custom_Injected_Blockstate");
            eventData = BlockEventGenerator.CreateNewBlockEvent(blockEventId, block, blockEventOverrideMap);
        } else {
            blockEventId = Identifier.fromString(blockstate.get("blockEventsId").asString().replaceAll("\"", "")+"_Custom_Injected_Blockstate_"+id.name);
            blockEventId = Identifier.fromString(blockstate.get("blockEventsId").asString().replaceAll("\"", "")+"_Custom_Injected_Blockstate_"+id.name);
            eventData = BlockEventGenerator.InjectIntoBlockEvent(
                    Identifier.fromString(blockstate.get("blockEventsId").asString().replaceAll("\"", "")),
                    blockEventId,
                    block,
                    blockEventOverrideMap
            );
        }
        blockstate.set("blockEventsId", blockEventId.toString());
        FluxRegistries.BLOCK_EVENTS.register(blockEventId, eventData);
        return blockstate;
    }

}