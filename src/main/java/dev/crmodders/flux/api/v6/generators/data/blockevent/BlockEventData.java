package dev.crmodders.flux.api.generators.data.blockevent;

import dev.crmodders.flux.api.generators.data.blockevent.triggers.TriggerData;
import dev.crmodders.flux.api.v6.suppliers.ReturnableSupplier;
import dev.crmodders.flux.tags.Identifier;
import org.hjson.JsonObject;

/**
 * The Structure of blockEvents used in {@link dev.crmodders.flux.api.generators.BlockEventGenerator}.
 *
 * @param parentEvent the parent event to base the events off of.
 * @param identifier the id for this block event.
 * @param triggers the triggers called during certain interactions with this event
 *
 * @param noParent this just disables parentEvent from being written
 */
public record BlockEventData(
        Identifier parentEvent,
        Identifier identifier,
        TriggerData[] triggers,

        boolean noParent
) implements BlockEventDataExt {

    public JsonObject toJson() {
        JsonObject blockEventData = new JsonObject();
        if (parentEvent != null) blockEventData.set("parent", parentEvent.toString());
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
