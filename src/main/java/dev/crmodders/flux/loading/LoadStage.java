package dev.crmodders.flux.loading;

import dev.crmodders.flux.api.gui.ProgressBarElement;
import dev.crmodders.flux.localization.TranslationKey;

import java.util.concurrent.ExecutorService;

public interface LoadStage {

    TranslationKey title();

    void doStage(ProgressBarElement progress, ExecutorService threadPool, ExecutorService glThread);

}
