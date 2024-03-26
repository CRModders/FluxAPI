package dev.crmodders.flux.api.generators.data.blockstate;

import dev.crmodders.flux.tags.Identifier;
import org.hjson.JsonObject;

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
