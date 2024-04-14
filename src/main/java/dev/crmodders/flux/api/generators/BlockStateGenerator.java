package dev.crmodders.flux.api.generators;

import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.generators.data.blockstate.BlockStateData;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import org.hjson.JsonObject;

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

    public static JsonObject ModifiyBlockState(Identifier id, IModBlock block, JsonObject oldBlockState, String stateName) {
        JsonObject blockstate = oldBlockState;
        Identifier blockEventId;
        BlockEventDataExt eventData;
        
        if (blockstate.get("blockEventsId") == null) {
            blockEventId = Identifier.fromString(id.toString() + "_Custom_Constructed_Blockstate");
            eventData = BlockEventGenerator.CreateNewBlockEvent(
                    blockEventId,
                    block,
                    stateName
            );
        } else {
            blockEventId = Identifier.fromString(blockstate.get("blockEventsId").asString().replaceAll("\"", "")+"_Custom_Injected_Blockstate_"+id.name);
            eventData = BlockEventGenerator.InjectIntoBlockEvent(
                    Identifier.fromString(blockstate.get("blockEventsId").asString().replaceAll("\"", "")),
                    blockEventId,
                    block,
                    stateName
            );
        }

        blockstate.set("blockEventsId", blockEventId.toString());
        if (blockstate.get("blockEventsId") != null) {
            blockstate.set("blockEventsId", blockstate.get("blockEventsId").asString().replaceAll("\"", ""));
        } else {
            blockstate.set("blockEventsId", eventData.toJson().get("stringId"));
        }
        FluxRegistries.BLOCK_EVENTS.register(blockEventId, eventData);
        return blockstate;
    }

}