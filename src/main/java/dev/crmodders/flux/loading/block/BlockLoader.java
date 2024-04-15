package dev.crmodders.flux.loading.block;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.mixins.accessor.BlockAccessor;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModel;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;

public class BlockLoader {

    public BlockModelFactory factory = new BlockModelFactory();

    /**
     * Call this method to register custom json models, this has to be called
     * before loading the block, else it will try to load it from disk, possibly
     * crashing the game or resulting in you block being replaced by the missing
     * block
     * @param modelName name of the model
     * @param rotXZ how to rotate the model valid values: 0, 90, 180, 270
     * @param modelJson regular json model from DataMods
     */
    public void registerBlockModel(String modelName, int rotXZ, String modelJson) {
        factory.createFromJson(modelName, rotXZ, modelJson);
    }

    public void registerCustomBlockModel(String modelName, float[] vertices, float[] uvs) {
        throw new IllegalStateException("Not implemented");
    }

    /**
     * Call this method to register custom textures instead of loading pngs from disk,
     * this has to be called before loading the block, else it will possibly crash the
     * game, due to it trying to load the texture from disk
     * @param textureName name of the texture, these are global be warned
     *                    about name collision, flux's block generator will
     *                    use a combination of model and texture names here
     * @param texture a pixmap representing your texture, this has to follow the guidelines
     *                from data modding, width and height have to be equal
     */
    public void registerTexture(String textureName, Pixmap texture) {
        CustomTextureLoader.registerTexture(textureName, texture);
    }

    public void registerEvent(String eventId, BlockEvents event) {
        BlockEvents.INSTANCES.put(eventId, event);
    }

    public void registerEventAction(String actionId, Class<? extends IBlockAction> actionClass) {
        BlockEvents.ALL_ACTIONS.put(actionId, actionClass);
    }

    /**
     * Call this method to load a block from json, see DataModding, it will use cached
     * models and textures, like those registered by registerBlockModel and registerTexture
     * @param block the block to be generated, for vanilla blocks the blockName attribute is
     *              used for Block.blocksByName
     * @return the block id extracted from the generated json
     */
    public Identifier loadBlock(IModBlock block) {
        BlockGenerator generator = block.getGenerator();
        String json = generator.createJSON(block);

        Block b = new Json().fromJson(Block.class, json);
        Array<String> blockStateKeysToAdd = b.blockStates.keys().toArray();

        Identifier blockId = Identifier.fromString(b.getStringId());

        try {
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
        } catch (Exception e) {
            for(BlockState blockState : b.blockStates.values()) {
                Block.allBlockStates.remove(blockState.stringId);
            }
            Block.allBlocks.removeValue(b, true);
            throw e;
        }

        Block.blocksByStringId.put(blockId.toString(), b);
        if(block instanceof VanillaModBlock vanillaModBlock) {
            Block.blocksByName.put(vanillaModBlock.blockName(), b);
        } else {
            Block.blocksByName.put(blockId.toString(), b);
        }

        return blockId;
    }

    /**
     * This has to be called on the OpenGL thread else the game
     * will hard crash with no error message
     */
    public void loadModels() {

        // initialize models, less parents first order
        for (BlockModel model : factory.sort()) {
            if(model instanceof BlockModelFlux flux) {
                BlockModelFactory.InstanceKey parentKey = new BlockModelFactory.InstanceKey(flux.parent, flux.rotXZ);
                BlockModelFlux parent = (BlockModelFlux) factory.models.get(parentKey);
                flux.initialize(parent);
            } else if (model instanceof  BlockModelVertex vertex) {
                vertex.initialize();
            }
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

    /**
     * This hooks the original block constants as those are not loaded statically
     * anymore, this has to be called after all blocks from the vanilla game are loaded
     * else it will try to load them here, crashing the game
     */
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
