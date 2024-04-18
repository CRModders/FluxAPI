package dev.crmodders.flux.api.v5.generators.helpers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import dev.crmodders.flux.api.v5.generators.BlockEventGenerator;
import finalforeach.cosmicreach.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.world.Zone;
import org.hjson.JsonObject;

import java.util.Map;

public class ModifiedBlockEventTrigger extends BlockEventTrigger {
    protected IBlockAction action;

    @Override
    public void write(Json json) {
        throw new RuntimeException("Not yet implemented!");
    }

    public void read(Json json, JsonValue jsonData) {
        String actionId = json.readValue(String.class, jsonData.get("actionId"));
        Class<? extends IBlockAction> actionClass = BlockEvents.ALL_ACTIONS.get(actionId);
        if (actionClass == null) {
            throw new RuntimeException("Could not find action for id: " + actionId);
        } else {
            if (actionClass == null) {
                throw new RuntimeException("Could not find action for id: " + actionId);
            } else {
                try {
                    this.action = json.fromJson(actionClass, jsonData.toString());
                } catch (Exception e) {
                    try {
                        this.action = BlockEventGenerator.ALL_ACTION_INSTANCES.get(actionId);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public IBlockAction getAction() {
        return this.action;
    }

    @Override
    public void act(BlockState srcBlockState, Zone zone, Map<String, Object> args) {
        this.action.act(srcBlockState, this, zone, args);
    }

    @Override
    public String toString() {
        return action.getActionId();
    }

    public static BlockEventTrigger fromJson(JsonObject object) {
        ModifiedBlockEventTrigger eventTrigger = new ModifiedBlockEventTrigger();
        try {
            JsonValue fromJson = new JsonReader().parse(object.toString());
            eventTrigger.read(new Json(), fromJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventTrigger;
    }
}