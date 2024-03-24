package io.github.crmodders.flux.api.generators;

import io.github.crmodders.flux.api.block.IFunctionalBlock;
import io.github.crmodders.flux.api.block.IModBlock;
import io.github.crmodders.flux.api.generators.data.blockevent.BlockEventData;
import io.github.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import io.github.crmodders.flux.api.generators.data.blockevent.BlockEventType;
import io.github.crmodders.flux.api.generators.data.blockstate.BlockStateData;
import io.github.crmodders.flux.api.generators.data.blockstate.BlockStateDataExt;
import io.github.crmodders.flux.api.suppliers.ReturnableDoubleInputSupplier;
import io.github.crmodders.flux.registry.StableRegistries;
import io.github.crmodders.flux.tags.Identifier;
import io.github.crmodders.flux.util.BlockBuilderUtils;
import org.hjson.JsonObject;

import java.util.HashMap;

public class BlockStateGenerator {

    JsonObject object;

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
            blockEventId = Identifier.fromString(blockstate.get("blockEventsId").asString().replaceAll("\"", "")+"_Custom_Injected_Blockstate");
            eventData = BlockEventGenerator.InjectIntoBlockEvent(
                    Identifier.fromString(blockstate.get("blockEventsId").asString().replaceAll("\"", "")),
                    blockEventId,
                    block,
                    blockEventOverrideMap
            );
        }
        blockstate.set("blockEventsId", blockEventId.toString());
        StableRegistries.BLOCK_EVENTS.register(blockEventId, eventData);
        return blockstate;
    }

}