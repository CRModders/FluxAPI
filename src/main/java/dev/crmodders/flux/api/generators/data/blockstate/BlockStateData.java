package dev.crmodders.flux.api.generators.data.blockstate;

import org.hjson.JsonObject;

public record BlockStateData(
        dev.crmodders.flux.tags.Identifier blockEventsId,
        String modelName
) implements BlockStateDataExt {

    public JsonObject toJson() {
        JsonObject blockStateData = new JsonObject();
        blockStateData.set("blockEventsId", blockEventsId.toString());
        blockStateData.set("modelName", modelName);
        return blockStateData;
    }

}
