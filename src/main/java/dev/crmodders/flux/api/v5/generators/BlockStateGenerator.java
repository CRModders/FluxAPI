package dev.crmodders.flux.api.v5.generators;

import dev.crmodders.flux.annotations.Stable;
import dev.crmodders.flux.api.v5.block.IModBlock;
import dev.crmodders.flux.api.v5.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.v5.generators.data.blockstate.BlockStateData;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import org.hjson.JsonObject;

/**
 * The internal generation class for {@link BlockStateGenerator}
 */

@Stable
public class BlockStateGenerator {

    protected JsonObject object;

    /**
     * Creates a BlockStateGenerator
     */
    public static BlockStateGenerator createGenerator() {
        return new BlockStateGenerator();
    }

    /**
     * Creates a {@link BlockStateData} based on 2 params
     *
     * @param blockEventsId The Event to be used when this blockState is active.
     * @param modelName The model that is displayed when this state is loaded.
     *
     * @return A newly created BlockState
     */
    public static BlockStateData createBasicBlockState(Identifier blockEventsId, String modelName) {
        return new BlockStateData(
                blockEventsId, modelName
        );
    }

    protected static JsonObject ModifiyBlockState(Identifier id, IModBlock block, JsonObject oldBlockState, String stateName) {
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