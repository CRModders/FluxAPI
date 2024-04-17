package dev.crmodders.flux.api.v5.generators;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.annotations.Stable;
import dev.crmodders.flux.api.v5.block.IModBlock;
import dev.crmodders.flux.api.v5.generators.data.blockevent.BlockEventType;
import dev.crmodders.flux.api.v5.generators.data.blockstate.BlockStateData;
import dev.crmodders.flux.api.v5.generators.data.blockstate.BlockStateDataExt;
import dev.crmodders.flux.api.v5.generators.suppliers.BasicTriggerSupplier;
import dev.crmodders.flux.api.v5.resource.ResourceLocation;
import dev.crmodders.flux.api.v5.suppliers.ReturnableDoubleInputSupplier;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.BlockBuilderUtils;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJson;
import org.hjson.JsonObject;
import org.hjson.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * The class used to create a {@link Block} from Json or from a {@link IModBlock}.
 */
@Stable
public class BlockGenerator {

    protected JsonObject object;
    protected HashMap<BlockEventType, Boolean> blockEventOverrideMap;
    protected HashMap<BlockEventType, List<TriggerStatePair>> blockTriggers;
    protected HashMap<BlockEventType, List<BasicTriggerSupplier>> multiStateTriggers;
    protected boolean resourceDriven;
    protected ResourceLocation resourceId;
    protected Identifier stringId;

    protected BlockGenerator(boolean resourceDriven, ResourceLocation resourceId) {
        this.resourceDriven = resourceDriven;
        this.resourceId = resourceId;

        blockTriggers = new HashMap<>();
        blockTriggers.put(BlockEventType.OnPlace, new ArrayList<>());
        blockTriggers.put(BlockEventType.OnInteract, new ArrayList<>());
        blockTriggers.put(BlockEventType.OnBreak, new ArrayList<>());

        multiStateTriggers = new HashMap<>();
        multiStateTriggers.put(BlockEventType.OnPlace, new ArrayList<>());
        multiStateTriggers.put(BlockEventType.OnInteract, new ArrayList<>());
        multiStateTriggers.put(BlockEventType.OnBreak, new ArrayList<>());

        blockEventOverrideMap = new HashMap<>();
        blockEventOverrideMap.put(BlockEventType.OnPlace, false);
        blockEventOverrideMap.put(BlockEventType.OnInteract, false);
        blockEventOverrideMap.put(BlockEventType.OnBreak, false);

        object = new JsonObject();
        object.set("blockStates", new JsonObject());
        setBlockState("default", new BlockStateData(
               Identifier.fromString("base:block_events_default"),
                "base:model_metal_panel"
        ));


    }

    /**
     * Built to override default triggers provided by {@code block_events_default.json}
     * or any event provided by {@code BlockGenerator.createResourceDrivenGenerator}.
     *
     * @param eventType the Trigger type you want to override.
     * @param overrides a boolean to set if the event is to be overridden.
     */
    public BlockGenerator overrideEvent(BlockEventType eventType, boolean overrides) {
        blockEventOverrideMap.put(eventType, overrides);
        return this;
    }

    /**
     * Shows you if a trigger type is overridden or not.
     *
     * @param eventType The type of trigger you want to check for it being overridden.
     */
    public boolean overridesEvent(BlockEventType eventType) {
        return blockEventOverrideMap.get(eventType);
    }

    /**
     * A function to add a trigger to a {@link finalforeach.cosmicreach.blocks.BlockState} of your choosing along
     * with the event type you want to have it trigger on.
     *
     * @param eventType The event you want to have your trigger start on.
     * @param blockStateName The block state you want to add the trigger to.
     * @param trigger The trigger function to be activated.
     */
    public BlockGenerator addTriggerToBlockstate(BlockEventType eventType, String blockStateName, BasicTriggerSupplier trigger) {
        List<TriggerStatePair> blockTriggerz = blockTriggers.get(eventType);
        blockTriggerz.add(new TriggerStatePair(
                blockStateName,
                trigger
        ));

        blockTriggers.put(eventType, blockTriggerz);
        return this;
    }

    /**
     * A function to add a trigger to every block state on a block/block generator
     * along with the event type you want to have it trigger on.
     *
     * @param eventType The event you want to have your trigger start on.
     * @param trigger The trigger function to be activated.
     */
    public BlockGenerator addTriggerMultiStateTrigger(BlockEventType eventType, BasicTriggerSupplier trigger) {
        List<BasicTriggerSupplier> blockTriggerz = multiStateTriggers.get(eventType);
        blockTriggerz.add(trigger);

        multiStateTriggers.put(eventType, blockTriggerz);
        return this;
    }

    /**
     * A getter that gets the all the triggers that you desire by blockEvent and blockState.
     *
     * @param eventType The event the trigger(s) are on.
     * @param blockStateName The blockState you want to get the trigger(s) by.
     *
     * @return A list of Triggers registered with the eventType and blockState to be used in.
     */
    public List<BasicTriggerSupplier> getTriggersPairs(BlockEventType eventType, String blockStateName) {
        List<BasicTriggerSupplier> triggers = new ArrayList<>();
        for (TriggerStatePair triggerStatePair : blockTriggers.get(eventType)) {
            if (triggerStatePair.blockStateName().equals(blockStateName)) {
                triggers.add(triggerStatePair.triggerSupplier());
            }
        }
        return triggers;
    }

    /**
     * Sets the block stringId
     *
     * @param id the label the block takes on.
     */
    public BlockGenerator setStringId(Identifier id) {
        stringId = id;
        object.set("stringId", id.toString());
        return this;
    }

    /**
     * Sets replaces/sets the blockState based on name.
     *
     * @param state the blockState's name you want to set.
     * @param blockStateData the blockState you want to set.
     */
    private BlockGenerator setBlockState(String state, BlockStateDataExt blockStateData) {
        object.set("blockStates", object.get("blockStates").asObject().set(state, blockStateData.toJson()));
        return this;
    }

    /**
     * Creates a blank generator that goes off of what ID you register it as in {@link FluxRegistries}
     */
    public static BlockGenerator createGenerator() {
        return new BlockGenerator(false, null);
    }

    /**
     * Creates a blank generator that goes off of what ID you register it as in {@link FluxRegistries}
     * and uses the resourceLocation to get the base Block.
     *
     * @param blockResourceLocation the base block you want to base your block off of. Ex format {@code new ResourceLocation(MOD_ID, "BLOCKNAME.JSON")}
     */
    public static BlockGenerator createResourceDrivenGenerator(ResourceLocation blockResourceLocation) {
        return new BlockGenerator(true, blockResourceLocation);
    }

    /**
     * This method gets the generation factory that flux requires in order to get your block working and cannot be called until the registries are frozen.
     */
    public ReturnableDoubleInputSupplier<IModBlock, Identifier, FactoryFinalizer<Block>> GetGeneratorFactory() {

        return (block, id) -> {
            if (!FluxRegistries.BLOCKS.isFrozen()) throw new RuntimeException("CANNOT USE GENERATOR FACTORY BECAUSE REGISTRIES ARE NOT FROZEN YET");
            if (object.get("stringId") == null) object.set("stringId", id.toString());

            FileHandle dataBlock = null;
            try {
                if (resourceDriven) dataBlock = GameAssetLoader.loadAsset(resourceId.namespace + ":blocks/" + resourceId.name);
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
                if (stringId != null) object.set("stringId", stringId.toString());
                else object.set("stringId", id.toString());
            }

            addTriggerMultiStateTrigger(BlockEventType.OnBreak, block::onBreak);
            addTriggerMultiStateTrigger(BlockEventType.OnPlace, block::onPlace);
            addTriggerMultiStateTrigger(BlockEventType.OnInteract, block::onInteract);

            for (String name : object.get("blockStates").asObject().names()) {
                for (BlockEventType key : multiStateTriggers.keySet())
                    for (BasicTriggerSupplier trigger : multiStateTriggers.get(key))
                        addTriggerToBlockstate(key, name, trigger);

                if (object.get("modelName") != null) {
                    Identifier modelId = Identifier.fromString(object.get("modelName").asString());
                    ResourceLocation modelLocation = new ResourceLocation(modelId.namespace, "models/"+modelId);
                    FluxRegistries.FACTORY_FINALIZERS.register(
                            modelId,
                            new FactoryFinalizer<>(() -> {
                                BlockModelJson.getInstance(modelLocation.toString(), 0);
                                return null;
                            })
                    );
                }

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
