package dev.crmodders.flux.api.generators;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.OrderedMap;
import dev.crmodders.flux.api.factories.IGenerator;
import dev.crmodders.flux.loading.block.BlockLoader;
import dev.crmodders.flux.tags.Identifier;

import java.util.*;

public class BlockEventGenerator implements IGenerator {

    public static class Trigger {
        public String actionId = "";
        public Map<String, Object> parameters;
    }

    public Identifier eventId;

    public Map<String, List<Trigger>> triggers = new HashMap<>();

    public BlockEventGenerator(Identifier blockId, String eventName) {
        this.eventId = new Identifier(blockId.namespace, blockId.name + "_" + eventName + "_events");
    }

    public void createTrigger(String triggerName, Identifier actionId, Map<String, Object> parameters) {
        List<Trigger> list = null;
        if(triggers.containsKey(triggerName)) {
            list = triggers.get(triggerName);
        } else {
            list = new ArrayList<>();
            triggers.put(triggerName, list);
        }
        Trigger trigger = new Trigger();
        trigger.actionId = actionId.toString();
        trigger.parameters = parameters;
        list.add(trigger);
    }

    @Override
    public void register(BlockLoader loader) {}

    @Override
    public String generateJson() {
        Json json = new Json();
        json.setTypeName(null);
        OrderedMap<String, Trigger[]> triggers = new OrderedMap<>();
        for(String triggerName : triggers.keys()) {
            triggers.put(triggerName, this.triggers.get(triggerName).toArray(Trigger[]::new));
        }
        return """
               {"parent":"base:block_events_default","stringId":"%s","triggers":%s}
                """.formatted(eventId.toString(), json.toJson(triggers));
    }
}
