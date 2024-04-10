package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.events.GameEvents;
import dev.crmodders.flux.api.generators.FactoryFinalizer;
import dev.crmodders.flux.api.gui.ProgressBarElement;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.loading.TaskBatch;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;

import java.util.concurrent.ExecutorService;

public class Finalize implements LoadStage {
    @Override
    public TranslationKey title() {
        return new TranslationKey("fluxapi:loading_menu.finalizing");
    }

    @Override
    public void doStage(ProgressBarElement progress, ExecutorService threadPool, ExecutorService glThread) {
        AccessableRegistry<FactoryFinalizer<?>> registryAccess = FluxRegistries.FACTORY_FINALIZERS.access();
        progress.range = registryAccess.getRegisteredNames().length;

        for (Identifier finalizerId : registryAccess.getRegisteredNames()) {
            FactoryFinalizer<?> finalizer = registryAccess.get(finalizerId);
            glThread.submit(() -> {
                finalizer.finalizeFactory();
                progress.value++;
                LogWrapper.info("%s: Registered Block Finalizer: %s".formatted(GameLoader.TAG, finalizerId));
            });
        }

    }

}