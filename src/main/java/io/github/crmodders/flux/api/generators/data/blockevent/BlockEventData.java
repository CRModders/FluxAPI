package io.github.crmodders.flux.api.generators.data.blockevent;

import io.github.crmodders.flux.api.generators.data.blockevent.triggers.TriggerData;
import io.github.crmodders.flux.api.suppliers.ReturnableSupplier;
import io.github.crmodders.flux.tags.Identifier;
import org.hjson.JsonObject;

public record BlockEventData(
        Identifier parentEvent,
        Identifier identifier,
        TriggerData[] triggers
) implements BlockEventDataExt {

    public JsonObject toJson() {
        JsonObject blockEventData = new JsonObject();
        blockEventData.set("parent", parentEvent.toString());
        blockEventData.set("stringId", identifier.toString());
        blockEventData.set("triggers", ((ReturnableSupplier<JsonObject>)()->{
            JsonObject eventData = new JsonObject();
            for (TriggerData event : triggers) {
                eventData.set(event.name(), event.toJson().get(event.name()));
            }
            return eventData;
        }).get());
        return blockEventData;
    }

}
