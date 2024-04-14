package dev.crmodders.flux.api.generators;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventData;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventType;
import dev.crmodders.flux.api.generators.data.blockevent.triggers.TriggerData;
import dev.crmodders.flux.api.generators.data.blockevent.triggers.TriggerEventData;
import dev.crmodders.flux.api.generators.suppliers.BasicTriggerSupplier;
import dev.crmodders.flux.api.suppliers.ReturnableSupplier;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.PrivUtils;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blockevents.actions.*;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.Player;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.world.Zone;
import org.hjson.JsonArray;
import org.hjson.JsonObject;

import java.util.*;

public class BlockEventGenerator {

    public static Map<String, IBlockAction> ALL_ACTION_INSTANCES = new HashMap<>();


    public static void registerBlockEventAction(Identifier id, IBlockAction action) {
        ALL_ACTION_INSTANCES.put(id.toString(), action);
        BlockEvents.ALL_ACTIONS.put(id.toString(), action.getClass());
    }

    private static void registerBlockEvent(String id, List<BasicTriggerSupplier> triggers) {

        FluxRegistries.BLOCK_EVENT_ACTIONS.register(Identifier.fromString(id), new IBlockAction() {
            @Override
            public void act(BlockState blockState, BlockEventTrigger blockEventTrigger, Zone zone, Map<String, Object> map) {
                try {
                    for (BasicTriggerSupplier trigger : triggers) {
                        trigger.runTrigger(
                                zone,
                                (Player) PrivUtils.getPrivField(InGame.class, "player"),
                                blockState,
                                (BlockPosition) map.get("blockPos")
                        );
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String getActionId() {
                return id;
            }
        });
    }

    public static void RegisterBlockEventActions(Identifier id, IModBlock block, String blockStateName) {

        BlockGenerator generator = block.getGenerator();

        registerBlockEvent(id + "_INTERACT", generator.getTriggersPairs(
                BlockEventType.OnInteract,
                blockStateName
        ));

        registerBlockEvent(id + "_PLACE", generator.getTriggersPairs(
                BlockEventType.OnPlace,
                blockStateName
        ));

        registerBlockEvent(id + "_BREAK", generator.getTriggersPairs(
                BlockEventType.OnBreak,
                blockStateName
        ));

    }

    public static BlockEventData CreateNewBlockEvent(
            Identifier blockEventId,
            IModBlock block,
            String stateName
    ) {
        BlockGenerator generator = block.getGenerator();

        RegisterBlockEventActions(blockEventId, block, stateName);

        HashMap<String, Object> onPlaceParams_0 = new HashMap<>();
        onPlaceParams_0.put("xOff", 0);
        onPlaceParams_0.put("yOff", 0);
        onPlaceParams_0.put("zOff", 0);
        onPlaceParams_0.put("blockStateId", "self");

        HashMap<String, Object> onPlaceParams_1 = new HashMap<>();
        onPlaceParams_1.put("sound", "block-place.ogg");
        onPlaceParams_1.put("volume", 1);
        onPlaceParams_1.put("pitch", 1);
        onPlaceParams_1.put("pan", 0);

        HashMap<String, Object> onBreakParams_0 = new HashMap<>();
        onBreakParams_0.put("xOff", 0);
        onBreakParams_0.put("yOff", 0);
        onBreakParams_0.put("zOff", 0);
        onBreakParams_0.put("blockStateId", "base:air[default]");

        HashMap<String, Object> onBreakParams_1 = new HashMap<>();
        onBreakParams_1.put("sound", "block-break.ogg");
        onBreakParams_1.put("volume", 1);
        onBreakParams_1.put("pitch", 1);
        onBreakParams_1.put("pan", 0);

        return new BlockEventData(
                null,
                blockEventId,
                new TriggerData[]{
                        new TriggerData(
                                "onInteract",
                                new TriggerEventData[]{
                                        new TriggerEventData(
                                                Identifier.fromString(blockEventId +"_INTERACT"),
                                                new HashMap<>()
                                        )
                                }
                        ),
                        new TriggerData(
                                "onPlace",
                                ((ReturnableSupplier<TriggerEventData[]>)()->{
                                    List<TriggerEventData> data = new ArrayList<>();

                                    data.add(new TriggerEventData(
                                            Identifier.fromString(blockEventId +"_PLACE"),
                                            new HashMap<>()
                                    ));

                                    if (!generator.overridesEvent(BlockEventType.OnPlace)) {
                                        data.add(new TriggerEventData(
                                                        Identifier.fromString("base:replace_block_state"),
                                                        onPlaceParams_0
                                        ));
                                        data.add(new TriggerEventData(
                                                Identifier.fromString("base:play_sound_2d"),
                                                onPlaceParams_1
                                        ));
                                    }
                                    return data.toArray(new TriggerEventData[0]);
                                }).get()
                        ),
                        new TriggerData(
                                "onBreak",
                                ((ReturnableSupplier<TriggerEventData[]>)()->{
                                    List<TriggerEventData> data = new ArrayList<>();

                                    data.add(new TriggerEventData(
                                            Identifier.fromString(blockEventId +"_BREAK"),
                                            new HashMap<>()
                                    ));

                                    if (!generator.overridesEvent(BlockEventType.OnBreak)) {
                                        data.add(new TriggerEventData(
                                                Identifier.fromString("base:replace_block_state"),
                                                onBreakParams_0
                                        ));
                                        data.add(new TriggerEventData(
                                                Identifier.fromString("base:play_sound_2d"),
                                                onBreakParams_1
                                        ));
                                    }
                                    return data.toArray(new TriggerEventData[0]);
                                }).get()
                        )
                }, true
        );
    }

    public static BlockEventDataExt InjectIntoBlockEvent(Identifier oldId, Identifier blockEventId, IModBlock block, String blockStateName) {
        BlockGenerator generator = block.getGenerator();

        RegisterBlockEventActions(blockEventId, block, blockStateName);

        FileHandle f = GameAssetLoader.loadAsset(oldId.namespace + ":block_events/" + oldId.name + ".json");
        JsonObject jsonObject = JsonObject.readJSON(f.readString()).asObject();
        jsonObject.set("stringId", blockEventId.toString());

        if (jsonObject.get("triggers") == null) jsonObject.set("triggers", new JsonObject());

        boolean LazyInjected_Interact = false;
        boolean LazyInjected_Place = false;
        boolean LazyInjected_Break = false;

        for (String s : jsonObject.get("triggers").asObject().names()) {
            if (Objects.equals(s, "onInteract")) {
                if (!generator.overridesEvent(BlockEventType.OnInteract)) {
                    LazyInjected_Interact = true;
                    jsonObject.set("triggers", jsonObject.get("triggers").asObject()
                            .set(s, jsonObject.get("triggers").asObject().get(s).asArray().add(
                                    new JsonObject().set("actionId", blockEventId + "_INTERACT")
                            )));
                }
            }

            if (Objects.equals(s, "onPlace")) {
                if (!generator.overridesEvent(BlockEventType.OnPlace)) {
                    LazyInjected_Place = true;
                    jsonObject.set("triggers", jsonObject.get("triggers").asObject()
                            .set(s, jsonObject.get("triggers").asObject().get(s).asArray().add(
                                    new JsonObject().set("actionId", blockEventId + "_PLACE")
                            )));
                }
            }

            if (Objects.equals(s, "onBreak")) {
                if (!generator.overridesEvent(BlockEventType.OnBreak)) {
                    LazyInjected_Break = true;
                    jsonObject.set("triggers", jsonObject.get("triggers").asObject()
                            .set(s, jsonObject.get("triggers").asObject().get(s).asArray().add(
                                    new JsonObject().set("actionId", blockEventId + "_BREAK")
                            )));
                }
            }
        }

        if (!LazyInjected_Interact) {
            jsonObject.set("triggers", jsonObject.get("triggers").asObject()
                    .set("onInteract", new TriggerData(
                            "onInteract",
                            new TriggerEventData[]{
                                    new TriggerEventData(
                                            Identifier.fromString(blockEventId + "_INTERACT"),
                                            new HashMap<>()
                                    )
                            }
                    ).toJson().get("onInteract"))
            );
        }

        if (!LazyInjected_Place) {
            Map<String, Object> onPlaceParams_0 = new HashMap<>();
            onPlaceParams_0.put("xOff", 0);
            onPlaceParams_0.put("yOff", 0);
            onPlaceParams_0.put("zOff", 0);
            onPlaceParams_0.put("blockStateId", "self");

            Map<String, Object> onPlaceParams_1 = new HashMap<>();
            onPlaceParams_1.put("sound", "block-place.ogg");
            onPlaceParams_1.put("volume", 1);
            onPlaceParams_1.put("pitch", 1);
            onPlaceParams_1.put("pan", 0);

            jsonObject.set("triggers", jsonObject.get("triggers").asObject()
                    .set("onPlace", new TriggerData(
                                    "onPlace",
                                    ((ReturnableSupplier<TriggerEventData[]>)()->{
                                        List<TriggerEventData> data = new ArrayList<>();

                                        data.add(new TriggerEventData(
                                                Identifier.fromString(blockEventId +"_PLACE"),
                                                new HashMap<>()
                                        ));

                                        if (!generator.overridesEvent(BlockEventType.OnPlace)) {
                                            data.add(new TriggerEventData(
                                                    Identifier.fromString("base:replace_block_state"),
                                                    onPlaceParams_0
                                            ));
                                            data.add(new TriggerEventData(
                                                    Identifier.fromString("base:play_sound_2d"),
                                                    onPlaceParams_1
                                            ));
                                        }
                                        return data.toArray(new TriggerEventData[0]);
                                    }).get()
                            ).toJson().get("onPlace")
                    )
            );
        }

        if (!LazyInjected_Break) {
            Map<String, Object> onBreakParams_0 = new HashMap<>();
            onBreakParams_0.put("xOff", 0);
            onBreakParams_0.put("yOff", 0);
            onBreakParams_0.put("zOff", 0);
            onBreakParams_0.put("blockStateId", "base:air[default]");

            Map<String, Object> onBreakParams_1 = new HashMap<>();
            onBreakParams_1.put("sound", "block-break.ogg");
            onBreakParams_1.put("volume", 1);
            onBreakParams_1.put("pitch", 1);
            onBreakParams_1.put("pan", 0);

            jsonObject.set("triggers", jsonObject.get("triggers").asObject()
                    .set("onBreak", new TriggerData(
                                    "onBreak",
                                        ((ReturnableSupplier<TriggerEventData[]>)()->{
                                            List<TriggerEventData> data = new ArrayList<>();

                                            data.add(new TriggerEventData(
                                                    Identifier.fromString(blockEventId +"_BREAK"),
                                                    new HashMap<>()
                                            ));

                                            if (!generator.overridesEvent(BlockEventType.OnBreak)) {
                                                data.add(new TriggerEventData(
                                                        Identifier.fromString("base:replace_block_state"),
                                                        onBreakParams_0
                                                ));
                                                data.add(new TriggerEventData(
                                                        Identifier.fromString("base:play_sound_2d"),
                                                        onBreakParams_1
                                                ));
                                            }
                                            return data.toArray(new TriggerEventData[0]);
                                        }).get()
                            ).toJson().get("onBreak")
                    )
            );
        }

        return () -> jsonObject;
    }

    public static BlockEvents fromJson(JsonObject json) {
        NewBlockEvents events = new NewBlockEvents();
        if (json.get("parent") != null) events.parent = json.get("parent").asString();
        events.stringId = json.get("stringId").asString();

        JsonObject triggers = json.get("triggers").asObject();
        for (String trigger : triggers.names()) {
            List<BlockEventTrigger> eventTriggers = new ArrayList<>();
            JsonArray jsonValues = triggers.get(trigger).asArray();
            Iterator<org.hjson.JsonValue> jsonValueIterator = jsonValues.iterator();
            while (jsonValueIterator.hasNext()) {
                JsonObject object = jsonValueIterator.next().asObject();
                BlockEventTrigger eventTrigger = getNewBlockEventTrigger(object);
                eventTriggers.add(eventTrigger);
            }
            events.triggers.put(trigger, eventTriggers.toArray(new BlockEventTrigger[0]));
        }
        return events;
    }

    private static BlockEventTrigger getNewBlockEventTrigger(JsonObject object) {
        NewBlockEventTrigger eventTrigger = new NewBlockEventTrigger();
        try {
            JsonValue fromJson = new JsonReader().parse(object.toString());
            eventTrigger.read(new Json(), fromJson, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventTrigger;
    }

    public static class NewBlockEventTrigger extends BlockEventTrigger {
        public IBlockAction action;

        @Override
        public void write(Json json) {
            throw new RuntimeException("Not yet implemented!");
        }

        public void read(Json json, JsonValue jsonData, JsonObject object) {
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
                            this.action = ALL_ACTION_INSTANCES.get(actionId);
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
    }

    private static class NewBlockEvents extends BlockEvents {
        public String parent;
        public String stringId;
        public OrderedMap<String, BlockEventTrigger[]> triggers = new OrderedMap<>();
        public transient boolean initTriggers;

        public BlockEvents getParent() {
            return this.parent == null ? null : getInstance(this.parent);
        }

        @Override
        public OrderedMap<String, BlockEventTrigger[]> getTriggerMap() {
            if (!this.initTriggers) {
                BlockEvents parentEvent = this.getParent();
                if (parentEvent != null) {
                    OrderedMap<String, BlockEventTrigger[]> parentTriggers = parentEvent.getTriggerMap();
                    if (parentTriggers != null) {
                        ObjectMap.Entries var3 = parentTriggers.entries().iterator();
                        while(var3.hasNext()) {
                            ObjectMap.Entry<String, BlockEventTrigger[]> t = (ObjectMap.Entry)var3.next();
                            if (!this.triggers.containsKey(t.key)) {
                                this.triggers.put(t.key, t.value);
                            }
                        }
                    }
                }

                this.initTriggers = true;
            }

            return this.triggers;
        }

        @Override
        public BlockEventTrigger[] getTriggers(String triggerId) {
            OrderedMap<String, BlockEventTrigger[]> triggers = this.getTriggerMap();
            return triggers.get(triggerId);
        }

    }

}
