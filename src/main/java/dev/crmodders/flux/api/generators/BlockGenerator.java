package dev.crmodders.flux.api.generators;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventType;
import dev.crmodders.flux.api.generators.data.blockstate.BlockStateData;
import dev.crmodders.flux.api.generators.data.blockstate.BlockStateDataExt;
import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.api.suppliers.ReturnableDoubleInputSupplier;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.BlockBuilderUtils;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.blocks.Block;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.pmw.tinylog.Logger;

import java.util.HashMap;

public class BlockGenerator {

    protected JsonObject object;
    protected HashMap<BlockEventType, Boolean> blockEventOverrideMap;
    protected boolean resourceDriven;
    protected ResourceLocation resourceId;
    protected Identifier blockId;

    protected BlockGenerator(Identifier blockId) {
        this(false, null, blockId);
    }

    protected BlockGenerator(boolean resourceDriven, ResourceLocation resourceId, Identifier blockId) {
        this.resourceDriven = resourceDriven;
        this.resourceId = resourceId;
        this.blockId = blockId;

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

    public BlockGenerator setStringId(Identifier id) {
        object.set("stringId", id.toString());
        return this;
    }
    private BlockGenerator setBlockState(String state, BlockStateDataExt blockStateData) {
        object.set("blockStates", object.get("blockStates").asObject().set(state, blockStateData.toJson()));
        return this;
    }

    public static BlockGenerator createGenerator(Identifier blockId) {
        return new BlockGenerator(blockId);
    }

    public static BlockGenerator createResourceDrivenGenerator(ResourceLocation preExistingBlockId) {
        return new BlockGenerator(true, preExistingBlockId, null);
    }

    public String getJson(IModBlock block) {
        if(blockId != null) {
            object.set("stringId", blockId.toString());
        }

        FileHandle dataBlock = null;
        try {
            if(blockId != null) {
                dataBlock = GameAssetLoader.loadAsset(blockId.namespace + ":blocks/" + blockId.name + ".json");
            }
            if (dataBlock == null && resourceDriven) {
                dataBlock = GameAssetLoader.loadAsset(resourceId.namespace + ":blocks/" + resourceId.name + ".json");
            }
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
            if(blockId == null) {
                blockId = Identifier.fromString(object.get("stringId").asString());
            }
        }

//        for (String name : object.get("blockStates").asObject().names()) {
//            JsonObject newBlockstate = BlockStateGenerator.ModifiyBlockState(
//                    blockId,
//                    block,
//                    object.get("blockStates").asObject().get(name).asObject()
//            );
//            object.set("blockStates", object.get("blockStates").asObject().set(name, newBlockstate));
//        }

        if(blockId == null) {
            throw new RuntimeException("error loading block");
        }

        return object.toString();
    }

    public Identifier getBlockId() {
        return blockId;
    }
}
