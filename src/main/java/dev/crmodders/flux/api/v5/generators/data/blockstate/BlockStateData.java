package dev.crmodders.flux.api.v5.generators.data.blockstate;

import dev.crmodders.flux.tags.Identifier;
import org.hjson.JsonObject;

/**
 * A datastructure built for making basic blockStates from events and models.
 *
 * @param blockEventsId The Event to be used when this blockState is active.
 * @param modelName The model that is displayed when this state is loaded.
 */
public record BlockStateData(
        Identifier blockEventsId,
        String modelName
) implements BlockStateDataExt {

    public JsonObject toJson() {
        JsonObject blockStateData = new JsonObject();
        blockStateData.set("blockEventsId", blockEventsId.toString());
        blockStateData.set("modelName", modelName);
        return blockStateData;
    }

}
