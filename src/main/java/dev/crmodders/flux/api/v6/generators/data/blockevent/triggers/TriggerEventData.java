package dev.crmodders.flux.api.v6.generators.data.blockevent.triggers;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import dev.crmodders.flux.api.v6.generators.data.DataJson;
import dev.crmodders.flux.api.v6.generators.data.blockevent.BlockEventData;
import dev.crmodders.flux.api.v6.suppliers.ReturnableSupplier;
import dev.crmodders.flux.tags.Identifier;
import org.hjson.JsonObject;
import org.hjson.JsonValue;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * The trigger activated during the parent Interation Trigger
 *
 * @param actionID the code-based-action loaded on activation
 * @param parameters the params passed into the action from actionID
 */
public record TriggerEventData(
        Identifier actionID,
        Map<String, Object> parameters
) implements DataJson {

    public JsonObject toJson() {
        JsonObject TEventData = new JsonObject();
        TEventData.set("actionId", actionID.toString());
        TEventData.set("parameters", ((ReturnableSupplier<JsonObject>)()->{
            Type typeObject = new TypeToken<HashMap>() {}.getType();
            JsonObject params = JsonValue.readJSON(new Gson().toJson(parameters, typeObject)).asObject();
            return params;
        }).get());
        return TEventData;
    }

}
