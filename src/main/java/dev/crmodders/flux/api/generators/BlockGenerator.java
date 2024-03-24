package dev.crmodders.flux.api.generators;

import dev.crmodders.flux.api.generators.BlockStateGenerator;
import finalforeach.cosmicreach.world.blocks.Block;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.block.IFunctionalBlock;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventType;
import dev.crmodders.flux.api.generators.data.blockstate.BlockStateData;
import dev.crmodders.flux.api.generators.data.blockstate.BlockStateDataExt;
import dev.crmodders.flux.api.suppliers.ReturnableDoubleInputSupplier;
import dev.crmodders.flux.registry.StableRegistries;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.BlockBuilderUtils;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.hjson.Stringify;

import java.util.HashMap;

public class BlockGenerator {

    JsonObject object;
    HashMap<BlockEventType, Boolean> blockEventOverrideMap;

    protected BlockGenerator() {
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

    public BlockGenerator overridesBlockEvent(BlockEventType eventType, boolean overrides) {
        blockEventOverrideMap.put(eventType, overrides);
        return this;
    }

    public BlockGenerator setStringId(Identifier id) {
        object.set("stringId", id.toString());
        return this;
    }
    public BlockGenerator setBlockState(String state, BlockStateDataExt blockStateData) {
        object.set("blockStates", object.get("blockStates").asObject().set(state, blockStateData.toJson()));
        return this;
    }

    public BlockGenerator modifyBlockState(String state, String field, JsonValue value) {
        JsonObject states = object.get("blockStates").asObject();
        JsonObject oldState = object.get("blockStates").asObject().get(state).asObject();
        object.set("blockStates", states.set(state, oldState.set(field, value)));
        return this;
    }

    public static BlockGenerator createGenerator() {
        return new BlockGenerator();
    }

    public ReturnableDoubleInputSupplier<IModBlock, Identifier, FactoryFinalizer> GetGeneratorFactory() {
        return (block, id) -> {
            if (!StableRegistries.BLOCKS.isFrozen()) throw new RuntimeException("CANNOT USE GENERATOR FACTORY BECAUSE REGISTRIES ARE NOT FROZEN YET");
            if (object.get("stringId") == null) object.set("stringId", id.toString());

            if (block instanceof IFunctionalBlock) {
                for (String name : object.get("blockStates").asObject().names()) {
                    JsonObject newBlockstate = BlockStateGenerator.ModifiyBlockState(
                            id,
                            (IFunctionalBlock) block,
                            object.get("blockStates").asObject().get(name).asObject(),
                            blockEventOverrideMap
                    );
                    object.set("blockStates", object.get("blockStates").asObject().set(name, newBlockstate));
                }
            }

            return new FactoryFinalizer(id, object);
        };
    }

    public static class FactoryFinalizer {

        Identifier id;
        JsonObject blockData;

        public FactoryFinalizer(Identifier id, JsonObject blockData) {
            this.id = id;
            this.blockData = blockData;
        }

        public Block finalizeFactory() {
            return BlockBuilderUtils.getBlockFromJson(id, blockData.toString());
        }
    }

}
