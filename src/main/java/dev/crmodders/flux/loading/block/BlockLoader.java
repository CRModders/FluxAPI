package dev.crmodders.flux.loading.block;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.loading.block.model.BlockModelFlux;
import dev.crmodders.flux.mixins.accessor.BlockAccessor;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModel;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJson;
import finalforeach.cosmicreach.rendering.blockmodels.IBlockModelInstantiator;
import org.lwjgl.system.linux.Stat;
import org.pmw.tinylog.Logger;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class BlockLoader implements IBlockModelInstantiator {
    private void generateSlab(String stateKey, BlockState oldState, String modelSlabType, int rotXZ, List<BlockState> newBlockStates) {
        boolean shouldHide = oldState.catalogHidden;
        String stateSlabType = modelSlabType;
        if (modelSlabType.equals("slab_vertical")) {
            switch (rotXZ) {
                case 0:
                    stateSlabType = "verticalNegX";
                    break;
                case 90:
                    stateSlabType = "verticalNegZ";
                    shouldHide = true;
                    break;
                case 180:
                    stateSlabType = "verticalPosX";
                    shouldHide = true;
                    break;
                case 270:
                    stateSlabType = "verticalPosZ";
                    shouldHide = true;
            }
        } else {
            stateSlabType = modelSlabType.replace("slab_", "");
        }

        if (modelSlabType.equals("slab_top")) {
            shouldHide = true;
        }

        Block b = oldState.getBlock();
        BlockState blockState = oldState.copy();
        blockState.generateSlabs = false;
        blockState.isOpaque = false;
        blockState.catalogHidden = shouldHide;
        String modelName = b.getStringId() + "[" + stateKey + "]_model_" + modelSlabType;
        GameSingletons.blockModelInstantiator.createSlabInstance(modelName, blockState, modelSlabType, rotXZ);
        blockState.initialize(b);
        blockState.setBlockModel(modelName, rotXZ);
        blockState.stringId = stateKey + ",slab_type=" + stateSlabType;
        newBlockStates.add(blockState);
    }

    private List<BlockState> generateSlabs(String stateKey, BlockState state) {
        Block b = state.getBlock();
        List<BlockState> newBlockStates = new ArrayList<>();
        generateSlab(stateKey, state, "slab_bottom", 0, newBlockStates);
        generateSlab(stateKey, state, "slab_top", 0, newBlockStates);
        generateSlab(stateKey, state, "slab_vertical", 0, newBlockStates);
        generateSlab(stateKey, state, "slab_vertical", 90, newBlockStates);
        generateSlab(stateKey, state, "slab_vertical", 180, newBlockStates);
        generateSlab(stateKey, state, "slab_vertical", 270, newBlockStates);
        for (BlockState newBlockState : newBlockStates) {
            b.blockStates.put(newBlockState.stringId, newBlockState);
        }
        return newBlockStates;
    }

    public final Map<BlockModelFlux.BlockModelJsonInstanceKey, BlockModelFlux> models = new HashMap<>();

    public Identifier loadBlock(IModBlock block) {
        BlockGenerator generator = block.getGenerator();

        String json = generator.getJson(block);
        Identifier blockId = generator.getBlockId();
        Block b = new Json().fromJson(Block.class, json);

        for(String stateKey : b.blockStates.keys()) {
            BlockState blockState = b.blockStates.get(stateKey);
            blockState.initialize(b);
            blockState.stringId = stateKey;

            Block.allBlockStates.put(blockState.stringId, blockState);
            if (blockState.generateSlabs) {
                for(BlockState state : generateSlabs(blockState.stringId, blockState)) {
                    Block.allBlockStates.put(state.stringId, state);
                }
            }

        }

        Block.blocksByStringId.put(blockId.toString(), b);
        if(block instanceof VanillaModBlock vanillaModBlock) {
            Block.blocksByName.put(vanillaModBlock.blockName(), b);
        } else {
            Block.blocksByName.put(blockId.toString(), b);
        }

        return blockId;
    }

    @Override
    public BlockModel getInstance(String modelName, int rotXZ) {
        final BlockModelFlux.BlockModelJsonInstanceKey key = new BlockModelFlux.BlockModelJsonInstanceKey(modelName, rotXZ);
        if(models.containsKey(key)) {
            return models.get(key);
        }
        BlockModelFlux model = BlockModelFlux.getInstance(modelName, rotXZ);
        models.put(key, model);
        return model;
    }

    @Override
    public void createSlabInstance(String modelName, BlockState blockState, String modelSlabType, int rotXZ) {
        final BlockModelFlux.BlockModelJsonInstanceKey key = new BlockModelFlux.BlockModelJsonInstanceKey(modelName, rotXZ);
        if(models.containsKey(key)) {
            return;
        }
        Json json = new Json();
        BlockModelFlux oldModel = (BlockModelFlux) blockState.getModel();
        String modelJson = "{\"parent\": \"" + modelSlabType + "\", \"textures\":";
        modelJson = modelJson + json.toJson(oldModel.getTextures());
        modelJson = modelJson + "}";
        BlockModelFlux model = BlockModelFlux.getInstanceFromJsonStr(modelName, modelJson, rotXZ);
        models.put(key, model);
    }

    public void loadModels() {

        for (Map.Entry<BlockModelFlux.BlockModelJsonInstanceKey, BlockModelFlux> entry : models.entrySet()) {
            BlockModelFlux model = entry.getValue();
            model.initialize();
        }

        for (BlockState blockState : Block.allBlockStates.values()) {
            BlockModel model = blockState.getModel();
            blockState.isPosXFaceOccluding = model.isPosXFaceOccluding && !blockState.isTransparent;
            blockState.isNegXFaceOccluding = model.isNegXFaceOccluding && !blockState.isTransparent;
            blockState.isPosYFaceOccluding = model.isPosYFaceOccluding && !blockState.isTransparent;
            blockState.isNegYFaceOccluding = model.isNegYFaceOccluding && !blockState.isTransparent;
            blockState.isPosZFaceOccluding = model.isPosZFaceOccluding && !blockState.isTransparent;
            blockState.isNegZFaceOccluding = model.isNegZFaceOccluding && !blockState.isTransparent;
            blockState.isPosXFacePartOccluding = model.isPosXFacePartOccluding;
            blockState.isNegXFacePartOccluding = model.isNegXFacePartOccluding;
            blockState.isPosYFacePartOccluding = model.isPosYFacePartOccluding;
            blockState.isNegYFacePartOccluding = model.isNegYFacePartOccluding;
            blockState.isPosZFacePartOccluding = model.isPosZFacePartOccluding;
            blockState.isNegZFacePartOccluding = model.isNegZFacePartOccluding;
            if(model instanceof BlockModelFlux fluxModel) {

            } else {
                Logger.error("Block has non Flux Model, big issue");
            }
        }

    }

    public void hookBlockManager() {
        BiConsumer<String, Block> setBlockStaticFinalField = (name, block) -> {
            try {

                Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                Unsafe unsafe = unsafe = (Unsafe) unsafeField.get(null);

                Field field = Block.class.getDeclaredField(name);
                Object fieldBase = unsafe.staticFieldBase(field);
                long fieldOffset = unsafe.staticFieldOffset(field);
                unsafe.putObject(fieldBase, fieldOffset, block);

            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        };

        setBlockStaticFinalField.accept("AIR", Block.getInstance("block_air"));
        setBlockStaticFinalField.accept("GRASS", Block.getInstance("block_grass"));
        setBlockStaticFinalField.accept("STONE_BASALT", Block.getInstance("block_stone_basalt"));
        setBlockStaticFinalField.accept("DIRT", Block.getInstance("block_dirt"));
        setBlockStaticFinalField.accept("WOODPLANKS", Block.getInstance("block_wood_planks"));
        setBlockStaticFinalField.accept("HAZARD", Block.getInstance("block_hazard"));
        setBlockStaticFinalField.accept("SAND", Block.getInstance("block_sand"));
        setBlockStaticFinalField.accept("TREELOG", Block.getInstance("block_tree_log"));
        setBlockStaticFinalField.accept("SNOW", Block.getInstance("block_snow"));
        setBlockStaticFinalField.accept("WATER", Block.getInstance("block_water"));
        setBlockStaticFinalField.accept("LUNAR_SOIL", Block.getInstance("block_lunar_soil"));

    }

}
