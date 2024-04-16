package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.api.assets.VanillaAssetLocations;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.block.impl.DataModBlock;
import dev.crmodders.flux.api.factories.FactoryFinalizer;
import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.loading.block.BlockLoadException;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;

import java.util.ArrayList;
import java.util.List;

import static dev.crmodders.flux.loading.GameLoader.logger;

public class InitializingCosmicReach extends LoadStage {


    private final List<String> blockNames = new ArrayList<>();

    @Override
    public void initialize(GameLoader loader) {
        super.initialize(loader);
        title = new TranslationKey("fluxapi:loading_menu.loading_cosmic_reach");

    }

    @Override
    public void doStage() {
        super.doStage();

        for(ResourceLocation internal : VanillaAssetLocations.getInternalFiles("blocks/", ".json")) {
            blockNames.add(internal.name.replace("blocks/", "").replace(".json", ""));
        }

        for(ResourceLocation internal : VanillaAssetLocations.getVanillaModFiles("blocks/", ".json")) {
            blockNames.add(internal.name.replace("blocks/", "").replace(".json", ""));
        }

        for(String blockName : blockNames) {
            FluxRegistries.BLOCK_FACTORIES.add(new FactoryFinalizer<>(() -> new DataModBlock(blockName)));
        }

        loader.setupProgressBar(loader.progress2, blockNames.size(), "Building Blocks");
        for(FactoryFinalizer<IModBlock> blockFactory : FluxRegistries.BLOCK_FACTORIES) {
            loader.incrementProgress(loader.progress2);
            try {
                IModBlock block = blockFactory.finalizeFactory();
                Identifier blockId = loader.blockLoader.loadBlock(block);
                FluxRegistries.BLOCKS.register(blockId, block);
            } catch (BlockLoadException e) {
                logger.warn("Cannot load block: \"{}\"", e.blockName, e.getCause());
            }
        }

    }

}
