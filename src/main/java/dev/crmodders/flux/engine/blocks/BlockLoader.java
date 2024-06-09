package dev.crmodders.flux.engine.blocks;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.FluxRegistries;
import dev.crmodders.flux.block.FluxBlockAction;
import dev.crmodders.flux.block.IModBlock;
import dev.crmodders.flux.engine.blocks.models.BlockModelFlux;
import dev.crmodders.flux.factories.IFactory;
import dev.crmodders.flux.generators.BlockEventGenerator;
import dev.crmodders.flux.generators.BlockGenerator;
import dev.crmodders.flux.generators.BlockModelGenerator;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.tags.ResourceLocation;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModel;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static dev.crmodders.flux.assets.FluxGameAssetLoader.LOADER;

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

    /**
     * Call this method to register custom textures instead of loading pngs from disk,
     * this has to be called before loading the block, else it will possibly crash the
     * game, due to it trying to load the texture from disk
     * @param textureName name of the texture, these are global be warned
     *                    about name collision, flux's block generator will
     *                    use a combination of model and texture names here
     * @param texture a pixmap representing your texture, this has to follow the guidelines
     *                from data modding, width and height have to be equal
     *                Note: this method does not take ownership of the Pixmap
     */
    public void registerTexture(String textureName, Pixmap texture) {
        CustomTextureLoader.registerTexture(textureName, texture);
    }

    public void registerTexture(ResourceLocation texture) {
        Pixmap pixmap = LOADER.loadResourceSync(texture, Pixmap.class);
        CustomTextureLoader.registerTexture(texture.toString(), pixmap);
        LOADER.unloadResource(texture);
    }

    /**
     * Call this method to register custom block events instead of loading them from
     * json files
     * @param eventName the id
     * @param eventJson the json
     */
    public void registerEvent(String eventName, String eventJson) {
        Json json = new Json();
        BlockEvents blockEvents = json.fromJson(BlockEvents.class, eventJson);
        BlockEvents.INSTANCES.put(eventName, blockEvents);
    }

    /**
     * Registers a block event action
     * @param actionId the id
     * @param action the action
     */
    public void registerEventAction(Identifier actionId, IFactory<FluxBlockAction> action) {
        FluxRegistries.BLOCK_EVENT_ACTION_FACTORIES.register(actionId, action);
    }

    /**
     * Call this method to load a block, it will use cached models and textures,
     * like those registered by registerBlockModel and registerTexture
     * @param modBlock the block to be generated
     * @return the block id extracted from the generated json
     */
    public Identifier loadBlock(IModBlock modBlock) {
        Json json = new Json();

        BlockGenerator blockGenerator = modBlock.getBlockGenerator();
        blockGenerator.register(this);
        String blockJson = blockGenerator.generateJson();
        Block block = json.fromJson(Block.class, blockJson);

        for(BlockModelGenerator modelGenerator : modBlock.getBlockModelGenerators(blockGenerator.blockId)) {
            modelGenerator.register(this);
            String modelName = modelGenerator.getModelName();
            int rotXZ = 0;
            String modelJson = modelGenerator.generateJson();
            registerBlockModel(modelName, rotXZ, modelJson);
        }

        List<BlockEventGenerator> eventGenerators = modBlock.getBlockEventGenerators(blockGenerator.blockId);
        if(eventGenerators.isEmpty()) {
            BlockEventGenerator eventGenerator = new BlockEventGenerator(blockGenerator.blockId, "flux_default");
            eventGenerators = List.of(eventGenerator);
        }
        for(BlockEventGenerator eventGenerator : eventGenerators) {
            eventGenerator.createTrigger("onInteract", Identifier.fromString("fluxapi:mod_block_interact"), Map.of("blockId", blockGenerator.blockId));
            eventGenerator.createTrigger("onPlace", Identifier.fromString("fluxapi:mod_block_place"), Map.of("blockId", blockGenerator.blockId));
            eventGenerator.createTrigger("onBreak", Identifier.fromString("fluxapi:mod_block_break"), Map.of("blockId", blockGenerator.blockId));
            eventGenerator.register(this);
            String eventName = eventGenerator.getEventName();
            String eventJson = eventGenerator.generateJson();
            registerEvent(eventName, eventJson);
        }

        try {
            for (String stateKey : block.blockStates.keys().toArray()) {
                BlockState blockState = block.blockStates.get(stateKey);
                blockState.stringId = stateKey;
                blockState.initialize(block);
                Block.allBlockStates.put(blockState.stringId, blockState);
            }
            Block.blocksByStringId.put(blockGenerator.blockId.toString(), block);
            Block.blocksByName.put(blockGenerator.blockName, block);
        } catch (Exception e) {
            for(BlockState blockState : block.blockStates.values()) {
                Block.allBlockStates.remove(blockState.stringId);
            }
            Block.allBlocks.removeValue(block, true);
            throw new BlockLoadException(blockGenerator.blockName, e);
        }
        return blockGenerator.blockId;
    }

    public void registerFinalizers() {

        // initialize models, fewer parents first order
        // it's very critical that registries are run in order here
        for (BlockModel model : factory.sort()) {
            if(model instanceof BlockModelFlux flux) {
                FluxRegistries.BLOCK_MODEL_FINALIZERS.register(Identifier.fromString(flux.modelName + "_" + flux.rotXZ), flux::initialize);
            }
        }
        FluxRegistries.BLOCK_MODEL_FINALIZERS.freeze();

        // fix culling flags
        for(Block block : Block.allBlocks) {
            for(BlockState blockState : block.blockStates.values()) {
                try {
                    if(blockState.getModel() instanceof BlockModelFlux model) {
                        String blockStateId = block.getStringId() + "[" + blockState.stringId + "]";
                        FluxRegistries.BLOCK_FINALIZERS.register(Identifier.fromString(blockStateId), () -> blockState.setBlockModel(model.modelName));
                    }
                } catch (Exception ignored) {}
            }
        }
        FluxRegistries.BLOCK_FINALIZERS.freeze();

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
