package dev.crmodders.flux.api.generators.data.blockevent.triggers;

import dev.crmodders.flux.api.generators.data.DataJson;
import dev.crmodders.flux.api.suppliers.ReturnableSupplier;
import dev.crmodders.flux.tags.Identifier;
import org.hjson.JsonObject;
import org.hjson.JsonValue;

import java.util.Map;

public record TriggerEventData(
        Identifier actionID,
        Map<String, Object> parameters
) implements DataJson {

    public JsonObject toJson() {
        JsonObject TEventData = new JsonObject();
        TEventData.set("actionId", actionID.toString());
        TEventData.set("parameters", ((ReturnableSupplier<JsonObject>)()->{
            JsonObject params = new JsonObject();
            for (String key : parameters.keySet()) {
                params.set(key, JsonValue.valueOfDsf(parameters.get(key)));
            }
            return params;
        }).get());
        return TEventData;
    }

}
