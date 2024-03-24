package dev.crmodders.flux.api.generators;

import com.badlogic.gdx.files.FileHandle;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.world.blockevents.IBlockEventAction;
import finalforeach.cosmicreach.world.blocks.BlockState;
import finalforeach.cosmicreach.world.entities.Player;
import dev.crmodders.flux.api.block.IFunctionalBlock;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventData;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventType;
import dev.crmodders.flux.api.generators.data.blockevent.triggers.TriggerData;
import dev.crmodders.flux.api.generators.data.blockevent.triggers.TriggerEventData;
import dev.crmodders.flux.api.suppliers.ReturnableSupplier;
import dev.crmodders.flux.registry.StableRegistries;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.PrivUtils;
import org.hjson.JsonObject;

import java.util.*;

public class BlockEventGenerator {

    public static void RegisterBlockEventActions(Identifier id, IFunctionalBlock block) {
        StableRegistries.BLOCK_EVENT_ACTIONS.register(Identifier.fromString(id.toString() + "_INTERACT"), new IBlockEventAction() {
            @Override
            public String getActionId() {
                return id + "_INTERACT";
            }

            @Override
            public void act(BlockState blockState, BlockEventTrigger blockEventTrigger, World world, Map<String, Object> map) {
                try {
                    block.onInteract(
                            world,
                            (Player) PrivUtils.getPrivField(InGame.class, "player"),
                            blockState,
                            (BlockPosition) map.get("blockPos")
                    );
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        StableRegistries.BLOCK_EVENT_ACTIONS.register(Identifier.fromString(id.toString()+"_PLACE"), new IBlockEventAction() {
            @Override
            public String getActionId() {
                return id+"_PLACE";
            }

            @Override
            public void act(BlockState blockState, BlockEventTrigger blockEventTrigger, World world, Map<String, Object> map) {
                try {
                    block.onPlace(
                            world,
                            (Player) PrivUtils.getPrivField(InGame.class, "player"),
                            blockState,
                            (BlockPosition) map.get("blockPos")
                    );
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        StableRegistries.BLOCK_EVENT_ACTIONS.register(Identifier.fromString(id.toString()+"_BREAK"), new IBlockEventAction() {
            @Override
            public String getActionId() {
                return id+"_BREAK";
            }

            @Override
            public void act(BlockState blockState, BlockEventTrigger blockEventTrigger, World world, Map<String, Object> map) {
                try {
                    block.onBreak(
                            world,
                            (Player) PrivUtils.getPrivField(InGame.class, "player"),
                            blockState,
                            (BlockPosition) map.get("blockPos")
                    );
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static BlockEventData CreateNewBlockEvent(Identifier blockEventId, IFunctionalBlock block, HashMap<BlockEventType, Boolean> blockEventOverrideMap) {
        RegisterBlockEventActions(blockEventId, block);

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
                Identifier.fromString("base:block_events_default"),
                blockEventId,
                new TriggerData[]{
                        new TriggerData(
                                "onInteract",
                                new TriggerEventData[]{
                                        new TriggerEventData(
                                                Identifier.fromString(blockEventId.toString()+"_INTERACT"),
                                                new HashMap<>()
                                        )
                                }
                        ),
                        new TriggerData(
                                "onPlace",
                                ((ReturnableSupplier<TriggerEventData[]>)()->{
                                    List<TriggerEventData> data = new ArrayList<>();

                                    data.add(new TriggerEventData(
                                            Identifier.fromString(blockEventId.toString()+"_PLACE"),
                                            new HashMap<>()
                                    ));

                                    if (!blockEventOverrideMap.get(BlockEventType.OnPlace)) {
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
                                            Identifier.fromString(blockEventId.toString()+"_BREAK"),
                                            new HashMap<>()
                                    ));

                                    if (!blockEventOverrideMap.get(BlockEventType.OnBreak)) {
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
                }
        );
    }

    public static BlockEventDataExt InjectIntoBlockEvent(Identifier oldId, Identifier blockEventId, IFunctionalBlock block, HashMap<BlockEventType, Boolean> blockEventOverrideMap) {
        RegisterBlockEventActions(blockEventId, block);

        FileHandle f = GameAssetLoader.loadAsset("block_events/" + oldId.name + ".json");
        JsonObject jsonObject = JsonObject.readJSON(f.readString()).asObject();
        if (jsonObject.get("triggers") == null) jsonObject.set("triggers", new JsonObject());

        boolean LazyInjected_Interact = false;
        boolean LazyInjected_Place = false;
        boolean LazyInjected_Break = false;

        for (String s : jsonObject.get("triggers").asObject().names()) {
            if (Objects.equals(s, "onInteract")) {
                if (!blockEventOverrideMap.get(BlockEventType.OnInteract)) {
                    LazyInjected_Interact = true;
                    jsonObject.set("triggers", jsonObject.get("triggers").asObject()
                            .set(s, jsonObject.get("triggers").asObject().get(s).asArray().add(
                                    new JsonObject().set("actionId", blockEventId + "_INTERACT")
                            )));
                }
            }
            if (Objects.equals(s, "onPlace")) {
                if (!blockEventOverrideMap.get(BlockEventType.OnPlace)) {
                    LazyInjected_Place = true;
                    jsonObject.set("triggers", jsonObject.get("triggers").asObject()
                            .set(s, jsonObject.get("triggers").asObject().get(s).asArray().add(
                                    new JsonObject().set("actionId", blockEventId + "_PLACE")
                            )));
                }
            }
            if (Objects.equals(s, "onBreak")) {
                if (!blockEventOverrideMap.get(BlockEventType.OnBreak)) {
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

            jsonObject.set("triggers", jsonObject.get("triggers").asObject()
                    .set("onPlace", new TriggerData(
                                    "onPlace",
                                    ((ReturnableSupplier<TriggerEventData[]>)()->{
                                        List<TriggerEventData> data = new ArrayList<>();

                                        data.add(new TriggerEventData(
                                                Identifier.fromString(blockEventId.toString()+"_PLACE"),
                                                new HashMap<>()
                                        ));

                                        if (!blockEventOverrideMap.get(BlockEventType.OnPlace)) {
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

            jsonObject.set("triggers", jsonObject.get("triggers").asObject()
                    .set("onBreak", new TriggerData(
                                    "onBreak",
                                        ((ReturnableSupplier<TriggerEventData[]>)()->{
                                            List<TriggerEventData> data = new ArrayList<>();

                                            data.add(new TriggerEventData(
                                                    Identifier.fromString(blockEventId.toString()+"_BREAK"),
                                                    new HashMap<>()
                                            ));

                                            if (!blockEventOverrideMap.get(BlockEventType.OnBreak)) {
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

        return new BlockEventDataExt() {
            @Override
            public JsonObject toJson() {
                return jsonObject;
            }
        };
    }

}
