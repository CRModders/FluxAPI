package dev.crmodders.flux.api.generators.data.blockevent.triggers;

import dev.crmodders.flux.api.generators.data.DataJson;
import dev.crmodders.flux.api.suppliers.ReturnableSupplier;
import org.hjson.JsonArray;
import org.hjson.JsonObject;

public record TriggerData(
    String name,
    TriggerEventData[] events
) implements DataJson {

    public JsonObject toJson() {
        JsonObject triggerData = new JsonObject();
        triggerData.set(name, ((ReturnableSupplier<JsonArray>)()->{
            JsonArray eventData = new JsonArray();
            for (TriggerEventData event : events) {
                eventData.add(event.toJson());
            }
            return eventData;
        }).get());
        return triggerData;
    }

}
