package dev.crmodders.flux.loading.block;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.loading.block.model.BlockModelFlux;
import dev.crmodders.flux.loading.block.model.BlockModelFluxCuboid;
import dev.crmodders.flux.mixins.accessor.BlockAccessor;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModel;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;

public class BlockLoader {

    public BlockModelFlux.Instantiator instantiator = new BlockModelFlux.Instantiator();

    public Identifier loadBlock(IModBlock block) {
        BlockGenerator generator = block.getGenerator();
        String json = generator.getJson(block);
        Identifier blockId = generator.getBlockId();

        Block b = new Json().fromJson(Block.class, json);
        Array<String> blockStateKeysToAdd = b.blockStates.keys().toArray();

        BlockState blockState;
        for(Array.ArrayIterator<String> it = blockStateKeysToAdd.iterator(); it.hasNext(); Block.allBlockStates.put(blockState.stringId, blockState)) {
            String stateKey = it.next();
            blockState = b.blockStates.get(stateKey);
            blockState.initialize(b);
            blockState.stringId = stateKey;
            if (blockState.generateSlabs) {
                BlockAccessor.generateSlabs(blockState.stringId, blockState);
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

    public void loadModels() {

        // initialize models, less parents first order
        for (BlockModelFlux model : instantiator.sort()) {
            BlockModelFlux.InstanceKey parentKey = new BlockModelFlux.InstanceKey(model.parent, model.rotXZ);
            BlockModelFlux parent = instantiator.models.get(parentKey);
            model.initialize(parent);
        }

        // fix culling flags
        for(Block block : Block.allBlocks) {
            for(BlockState blockState : block.blockStates.values()) {
                if(blockState.getModel() instanceof BlockModelFlux model) {
                    blockState.setBlockModel(model.modelName, model.rotXZ);
                }
            }
        }

    }

    public void hookBlockManager() {
        BiConsumer<String, Block> setBlockStaticFinalField = (name, block) -> {
            try {

                Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                Unsafe verySafeClassThatIsVeryUseful = (Unsafe) unsafeField.get(null);

                Field field = Block.class.getDeclaredField(name);
                Object fieldBase = verySafeClassThatIsVeryUseful.staticFieldBase(field);
                long fieldOffset = verySafeClassThatIsVeryUseful.staticFieldOffset(field);
                verySafeClassThatIsVeryUseful.putObject(fieldBase, fieldOffset, block);

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
