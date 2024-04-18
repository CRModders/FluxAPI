package dev.crmodders.flux.loading.stages.legacy;

import dev.crmodders.flux.annotations.LegacyInternal;
import dev.crmodders.flux.api.v5.generators.FactoryFinalizer;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;

import static dev.crmodders.flux.loading.GameLoader.logger;

@LegacyInternal
public class FinalizeBlocks extends LoadStage {

    @Override
    public void initialize(GameLoader loader) {
        super.initialize(loader);
        this.title = new TranslationKey("fluxapi:loading_menu.finalizing_blocks");
    }

    @Override
    public void doStage() {
        super.doStage();

        AccessableRegistry<FactoryFinalizer<?>> registryAccess = FluxRegistries.FACTORY_FINALIZERS.access();
        loader.progress2.range = registryAccess.getRegisteredNames().length;

        for (Identifier finalizerId : registryAccess.getRegisteredNames()) {
            FactoryFinalizer<?> finalizer = registryAccess.get(finalizerId);
            finalizer.finalizeFactory();
            loader.progress2.value++;
            logger.info("Registered Block Finalizer: {}", finalizerId);
        }

    }

}