package dev.crmodders.flux.engine.stages;

import dev.crmodders.flux.FluxRegistries;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.assets.VanillaAssetLocations;
import dev.crmodders.flux.block.DataModBlock;
import dev.crmodders.flux.block.IModBlock;
import dev.crmodders.flux.engine.GameLoader;
import dev.crmodders.flux.engine.LoadStage;
import dev.crmodders.flux.engine.blocks.BlockLoadException;
import dev.crmodders.flux.engine.blocks.actions.OnBreakTrigger;
import dev.crmodders.flux.engine.blocks.actions.OnInteractTrigger;
import dev.crmodders.flux.engine.blocks.actions.OnPlaceTrigger;
import dev.crmodders.flux.events.OnRegisterBlockEvent;
import dev.crmodders.flux.factories.IFactory;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.tags.ResourceLocation;
import finalforeach.cosmicreach.blockentities.BlockEntityCreator;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.items.loot.Loot;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static dev.crmodders.flux.engine.GameLoader.LOGGER;

public class LoadingCosmicReach extends LoadStage {

    @Override
    public void initialize(GameLoader loader) {
        super.initialize(loader);
        title = new TranslationKey("fluxapi:loading_menu.loading_cosmic_reach");
    }

    @Subscribe
    public void onEvent(OnRegisterBlockEvent event) {
        List<String> blockNames = new ArrayList<>();
        for(ResourceLocation internal : VanillaAssetLocations.getInternalFiles("blocks/", ".json")) {
            blockNames.add(internal.name.replace("blocks/", "").replace(".json", ""));
        }
        if(FluxSettings.EnabledVanillaMods.getValue()) {
            for(ResourceLocation internal : VanillaAssetLocations.getVanillaModFiles("blocks/", ".json")) {
                blockNames.add(internal.name.replace("blocks/", "").replace(".json", ""));
            }
        }

        for(String blockName : blockNames) {
            event.registerBlock(() -> new DataModBlock(blockName));
        }
    }

    @Override
    public void doStage() {
        super.doStage();

        BlockEvents.initBlockEvents();
        BlockEntityCreator.registerBlockEntityCreators();

        BlockEvents.registerBlockEventAction(OnPlaceTrigger.class);
        BlockEvents.registerBlockEventAction(OnBreakTrigger.class);
        BlockEvents.registerBlockEventAction(OnInteractTrigger.class);

        List<IFactory<IModBlock>> blockFactories = new ArrayList<>();
        FluxRegistries.EVENT_BUS.post(new OnRegisterBlockEvent(blockFactories));

        loader.setupProgressBar(loader.progressBar2, blockFactories.size(), new TranslationKey("fluxapi:loading_menu.creating_blocks"));
        for(IFactory<IModBlock> blockFactory : blockFactories) {
            loader.incrementProgress(loader.progressBar2);
            try {
                IModBlock block = blockFactory.generate();
                Identifier blockId = loader.blockLoader.loadBlock(block);
                System.out.println(blockId);
                FluxRegistries.BLOCKS.register(blockId, block);
            } catch (BlockLoadException e) {
                LOGGER.error("Cannot load block: \"{}\"", e.blockName, e);
                loader.blockLoader.errors.add(e);
            }
        }
        FluxRegistries.BLOCKS.freeze();

        loader.blockLoader.registerFinalizers();
        loader.blockLoader.hookOriginalBlockConstants();
    }

    @Override
    public List<Runnable> getGlTasks() {
        List<Runnable> tasks = super.getGlTasks();

        AccessableRegistry<Runnable> modelFinalizers = FluxRegistries.BLOCK_MODEL_FINALIZERS.access();
        Identifier[] modelIds = modelFinalizers.getRegisteredNames();
        AccessableRegistry<Runnable> blockFinalizers = FluxRegistries.BLOCK_FINALIZERS.access();
        Identifier[] blockStateIds = blockFinalizers.getRegisteredNames();

        tasks.add( () -> loader.setupProgressBar(loader.progressBar2, modelIds.length, "Creating Models") );
        for(Identifier modelId : modelIds) {
            tasks.add( () -> loader.incrementProgress(loader.progressBar2, modelId.toString()) );
            tasks.add( modelFinalizers.get(modelId) );
        }


        tasks.add( () -> loader.setupProgressBar(loader.progressBar2, blockStateIds.length, "Finalizing Blocks") );
        for(Identifier blockStateId : blockStateIds) {
            tasks.add( () -> loader.incrementProgress(loader.progressBar2, blockStateId.toString()) );
            tasks.add( blockFinalizers.get(blockStateId) );
        }

        tasks.add(Loot::loadLoot);

        return tasks;
    }
}
