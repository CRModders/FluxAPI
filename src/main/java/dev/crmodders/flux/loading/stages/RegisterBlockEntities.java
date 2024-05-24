package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.api.v5.entities.suppliers.FluxBlockEntityBuilder;
import dev.crmodders.flux.api.v5.generators.FactoryFinalizer;
import dev.crmodders.flux.api.v5.gui.ProgressBarElement;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blockentities.BlockEntityCreator;

import java.util.concurrent.ExecutorService;

public class RegisterBlockEntities implements LoadStage {

    public static int Count = 0;

    @Override
    public TranslationKey title() {
        return new TranslationKey("fluxapi:loading_menu.registering_block_entities");
    }

    @Override
    public void doStage(ProgressBarElement progress, ExecutorService threadPool, ExecutorService glThread) {
        AccessableRegistry<FluxBlockEntityBuilder> registryAccess = FluxRegistries.BLOCK_ENTITY_BUILDERS.access();
        progress.range = registryAccess.getRegisteredNames().length;

        FluxRegistries.BLOCKS.freeze();
        for (Identifier builderId : registryAccess.getRegisteredNames()) {
            FluxBlockEntityBuilder builder = registryAccess.get(builderId);
            glThread.submit(() -> {
                FluxRegistries.FACTORY_FINALIZERS.register(
                        builderId,
                        new FactoryFinalizer<>(
                                () -> {
                                    BlockEntityCreator.registerBlockEntityCreator(builderId.toString(), builder.convertBuilder());
                                    return null;
                                }
                        )
                );
                progress.value++;
                LogWrapper.info("%s: Registered Block Entity Builder: %s".formatted(GameLoader.TAG, builderId));
            });
        }

    }

}