package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.api.v5.generators.BlockEventGenerator;
import dev.crmodders.flux.api.v5.gui.ProgressBarElement;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blockevents.actions.ActionId;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;

import java.util.concurrent.ExecutorService;

public class RegisterBlockActions implements LoadStage {
    @Override
    public TranslationKey title() {
        return new TranslationKey("fluxapi:loading_menu.registering_block_event_actions");
    }

    @Override
    public void doStage(ProgressBarElement progress, ExecutorService threadPool, ExecutorService glThread) {
        AccessableRegistry<IBlockAction> registryAccess = FluxRegistries.BLOCK_EVENT_ACTIONS.access();
        progress.range = registryAccess.getRegisteredNames().length;

        FluxRegistries.BLOCK_EVENT_ACTIONS.freeze();
        for (Identifier actionId : registryAccess.getRegisteredNames()) {
            IBlockAction action = registryAccess.get(actionId);
            glThread.submit(() -> {
                BlockEventGenerator.registerBlockEventAction(actionId, action);
                progress.value++;
                LogWrapper.info("%s: Registered Block Event Action %s".formatted(GameLoader.TAG, action.getActionId()));
            });

        }
    }

}
