package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.api.events.GameEvents;
import dev.crmodders.flux.api.gui.ProgressBarElement;
import dev.crmodders.flux.api.resource.ResourceObject;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.loading.TaskBatch;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameAssetLoader;

import java.util.concurrent.ExecutorService;

public class Initialize implements LoadStage {
    @Override
    public TranslationKey title() {
        return new TranslationKey("fluxapi:loading_menu.initializing");
    }

    @Override
    public void doStage(ProgressBarElement progress, ExecutorService threadPool, ExecutorService glThread) {
        TaskBatch batch = new TaskBatch(threadPool);
        batch.submit(GameEvents.ON_GAME_INITIALIZED.invoker()::onInitialized);
        batch.await(glThread);
    }

}