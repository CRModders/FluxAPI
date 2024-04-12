package dev.crmodders.flux.api.generators;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventType;
import dev.crmodders.flux.api.generators.data.blockstate.BlockStateData;
import dev.crmodders.flux.api.generators.data.blockstate.BlockStateDataExt;
import dev.crmodders.flux.api.generators.suppliers.BasicTriggerSupplier;
import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.api.suppliers.ReturnableDoubleInputSupplier;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.BlockBuilderUtils;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.blocks.Block;
import org.hjson.JsonObject;
import org.hjson.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlockGenerator {

    protected JsonObject object;
    protected HashMap<BlockEventType, Boolean> blockEventOverrideMap;
    protected HashMap<BlockEventType, List<TriggerStatePair>> blockTriggers;
    protected HashMap<BlockEventType, List<BasicTriggerSupplier>> multiStateTriggers;
    protected boolean resourceDriven;
    protected ResourceLocation resourceId;

    protected BlockGenerator() {
        this(false, null);
    }

    protected BlockGenerator(boolean resourceDriven, ResourceLocation resourceId) {
        this.resourceDriven = resourceDriven;
        this.resourceId = resourceId;

        blockTriggers = new HashMap<>();
        blockTriggers.put(BlockEventType.OnPlace, new ArrayList<>());
        blockTriggers.put(BlockEventType.OnInteract, new ArrayList<>());
        blockTriggers.put(BlockEventType.OnBreak, new ArrayList<>());

        blockEventOverrideMap = new HashMap<>();
        blockEventOverrideMap.put(BlockEventType.OnPlace, false);
        blockEventOverrideMap.put(BlockEventType.OnInteract, false);
        blockEventOverrideMap.put(BlockEventType.OnBreak, false);

        object = new JsonObject();
        object.set("blockStates", new JsonObject());
        setBlockState("default", new BlockStateData(
               Identifier.fromString("base:block_events_default"),
                "model_metal_panel"
        ));

    }

    public BlockGenerator overrideEvent(BlockEventType eventType, boolean overrides) {
        blockEventOverrideMap.put(eventType, overrides);
        return this;
    }

    public boolean overridesEvent(BlockEventType eventType) {
        return blockEventOverrideMap.get(eventType);
    }

    public BlockGenerator addTriggerToBlockstate(BlockEventType eventType, String blockStateName, BasicTriggerSupplier trigger) {
        List<TriggerStatePair> blockTriggerz = blockTriggers.get(eventType);
        blockTriggerz.add(new TriggerStatePair(
                blockStateName,
                trigger
        ));

        blockTriggers.put(eventType, blockTriggerz);
        return this;
    }

    public BlockGenerator addTriggerMultiStateTrigger(BlockEventType eventType, BasicTriggerSupplier trigger) {
        List<BasicTriggerSupplier> blockTriggerz = multiStateTriggers.get(eventType);
        blockTriggerz.add(trigger);

        multiStateTriggers.put(eventType, blockTriggerz);
        return this;
    }

    public List<BasicTriggerSupplier> getTriggersPairs(BlockEventType eventType, String blockStateName) {
        List<BasicTriggerSupplier> triggers = new ArrayList<>();
        for (TriggerStatePair triggerStatePair : blockTriggers.get(eventType)) {
            if (triggerStatePair.blockStateName().equals(blockStateName)) {
                triggers.add(triggerStatePair.triggerSupplier());
            }
        }
        return triggers;
    }

    public BlockGenerator setStringId(Identifier id) {
        object.set("stringId", id.toString());
        return this;
    }

    private BlockGenerator setBlockState(String state, BlockStateDataExt blockStateData) {
        object.set("blockStates", object.get("blockStates").asObject().set(state, blockStateData.toJson()));
        return this;
    }

    public static BlockGenerator createGenerator() {
        return new BlockGenerator();
    }

    public static BlockGenerator createResourceDrivenGenerator(ResourceLocation preExistingBlockId) {
        return new BlockGenerator(true, preExistingBlockId);
    }

    public ReturnableDoubleInputSupplier<IModBlock, Identifier, FactoryFinalizer<Block>> GetGeneratorFactory() {

        return (block, id) -> {
            if (!FluxRegistries.BLOCKS.isFrozen()) throw new RuntimeException("CANNOT USE GENERATOR FACTORY BECAUSE REGISTRIES ARE NOT FROZEN YET");
            object.set("stringId", id.toString());

            FileHandle dataBlock = null;
            try {
                if (resourceDriven) dataBlock = GameAssetLoader.loadAsset(resourceId.namespace + ":blocks/" + resourceId.name + ".json");
                else dataBlock = GameAssetLoader.loadAsset(id.namespace + ":blocks/" + id.name + ".json");
            } catch (Exception ignore) {
            }

            if (dataBlock != null) {
                object = JsonValue.readJSON(
                        dataBlock.readString()
                                .replaceAll("\t", "")
                                .replaceAll(" ", "")
                                .replaceAll("\n", "")
                                .replaceAll(",}", "}")
                ).asObject();
            }

            addTriggerMultiStateTrigger(BlockEventType.OnBreak, block::onBreak);
            addTriggerMultiStateTrigger(BlockEventType.OnPlace, block::onPlace);
            addTriggerMultiStateTrigger(BlockEventType.OnInteract, block::onInteract);

            for (String name : object.get("blockStates").asObject().names()) {
                multiStateTriggers.keySet().forEach((key) -> multiStateTriggers.get(key).forEach((trigger) -> {
                    addTriggerToBlockstate(key, name, trigger);
                }));

                JsonObject newBlockstate = BlockStateGenerator.ModifiyBlockState(
                        id,
                        block,
                        object.get("blockStates").asObject().get(name).asObject(),
                        name
                );
                object.set("blockStates", object.get("blockStates").asObject().set(name, newBlockstate));
            }

            return new FactoryFinalizer<>(() -> BlockBuilderUtils.getBlockFromJson(id, object.toString()));
        };
    }

}
