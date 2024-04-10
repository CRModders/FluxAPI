package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.api.events.GameEvents;
import dev.crmodders.flux.api.events.system.RunnableArrayListEvent;
import dev.crmodders.flux.api.gui.ProgressBarElement;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.loading.TaskBatch;
import dev.crmodders.flux.localization.TranslationKey;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class PostInitialize implements LoadStage {
    @Override
    public TranslationKey title() {
        return new TranslationKey("fluxapi:loading_menu.initializing");
    }

    @Override
    public void doStage(ProgressBarElement progress, ExecutorService threadPool, ExecutorService glThread) {
        if(GameEvents.ON_POST_INIT instanceof RunnableArrayListEvent events) {
            List<Runnable> initializers = events.getRunnables();
            progress.range = initializers.size();
            progress.translation = new TranslationKey("fluxapi:loading_menu.post_init_phase");
            for(Runnable runnable : initializers) {
                glThread.submit(() -> progress.value++);
                glThread.submit(runnable);
            }
        } else {
            throw new RuntimeException("Can't run PostInit phase");
        }
    }

}