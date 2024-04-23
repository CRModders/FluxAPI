package dev.crmodders.flux.engine.stages;

import dev.crmodders.flux.api.assets.VanillaAssetLocations;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.block.impl.DataModBlock;
import dev.crmodders.flux.api.factories.IFactory;
import dev.crmodders.flux.engine.blocks.actions.OnBreakTrigger;
import dev.crmodders.flux.engine.blocks.actions.OnInteractTrigger;
import dev.crmodders.flux.engine.blocks.actions.OnPlaceTrigger;
import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.engine.GameLoader;
import dev.crmodders.flux.engine.LoadStage;
import dev.crmodders.flux.engine.blocks.BlockLoadException;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blockevents.BlockEvents;

import java.util.ArrayList;
import java.util.List;

import static dev.crmodders.flux.engine.GameLoader.logger;

public class LoadingCosmicReach extends LoadStage {


    private final List<String> blockNames = new ArrayList<>();

    @Override
    public void initialize(GameLoader loader) {
        super.initialize(loader);
        title = new TranslationKey("fluxapi:loading_menu.loading_cosmic_reach");

    }

    @Override
    public void doStage() {
        super.doStage();

        BlockEvents.registerBlockEventAction(OnPlaceTrigger.class);
        BlockEvents.registerBlockEventAction(OnBreakTrigger.class);
        BlockEvents.registerBlockEventAction(OnInteractTrigger.class);

        for(ResourceLocation internal : VanillaAssetLocations.getInternalFiles("blocks/", ".json")) {
            blockNames.add(internal.name.replace("blocks/", "").replace(".json", ""));
        }

        for(ResourceLocation internal : VanillaAssetLocations.getVanillaModFiles("blocks/", ".json")) {
            blockNames.add(internal.name.replace("blocks/", "").replace(".json", ""));
        }

        for(String blockName : blockNames) {
            FluxRegistries.BLOCK_FACTORIES.add(() -> new DataModBlock(blockName));
        }

        loader.setupProgressBar(loader.progress2, FluxRegistries.BLOCK_FACTORIES.size(), "Creating Blocks");
        for(IFactory<IModBlock> blockFactory : FluxRegistries.BLOCK_FACTORIES) {
            loader.incrementProgress(loader.progress2);
            try {
                IModBlock block = blockFactory.generate();
                Identifier blockId = loader.blockLoader.loadBlock(block);
                FluxRegistries.BLOCKS.register(blockId, block);
            } catch (BlockLoadException e) {
                logger.warn("Cannot load block: \"{}\"", e.blockName, e.getCause());
            }
        }
        FluxRegistries.BLOCKS.freeze();

        loader.blockLoader.registerFinalizers();
        loader.blockLoader.hookBlockManager();

    }

    @Override
    public List<Runnable> getGlTasks() {
        List<Runnable> tasks = super.getGlTasks();

        AccessableRegistry<Runnable> modelFinalizers = FluxRegistries.BLOCK_MODEL_FINALIZERS.access();
        Identifier[] modelIds = modelFinalizers.getRegisteredNames();
        AccessableRegistry<Runnable> blockFinalizers = FluxRegistries.BLOCK_FINALIZERS.access();
        Identifier[] blockStateIds = blockFinalizers.getRegisteredNames();

        tasks.add( () -> loader.setupProgressBar(loader.progress2, modelIds.length, "Creating Models") );
        for(Identifier modelId : modelIds) {
            tasks.add( () -> loader.incrementProgress(loader.progress2, modelId.toString()) );
            tasks.add( modelFinalizers.get(modelId) );
        }


        tasks.add( () -> loader.setupProgressBar(loader.progress2, blockStateIds.length, "Finalizing Blocks") );
        for(Identifier blockStateId : blockStateIds) {
            tasks.add( () -> loader.incrementProgress(loader.progress2, blockStateId.toString()) );
            tasks.add( blockFinalizers.get(blockStateId) );
        }

        return tasks;
    }
}
