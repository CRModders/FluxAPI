package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.api.events.GameEvents;
import dev.crmodders.flux.api.gui.ProgressBarElement;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.loading.TaskBatch;
import dev.crmodders.flux.localization.TranslationKey;

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