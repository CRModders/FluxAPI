package dev.crmodders.flux.loading.stages;

import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.gui.ProgressBarElement;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blockevents.BlockEvents;

import java.util.concurrent.ExecutorService;

public class RegisterBlockEvents implements LoadStage {
    @Override
    public TranslationKey title() {
        return new TranslationKey("fluxapi:loading_menu.registering_block_events");
    }

    @Override
    public void doStage(ProgressBarElement progress, ExecutorService threadPool, ExecutorService glThread) {
        FluxRegistries.BLOCK_EVENTS.freeze();
        AccessableRegistry<BlockEventDataExt> registryAccess = FluxRegistries.BLOCK_EVENTS.access();
        progress.range = registryAccess.getRegisteredNames().length;
        for (Identifier eventId : registryAccess.getRegisteredNames()) {
            BlockEventDataExt event = registryAccess.get(eventId);
            glThread.submit(() -> {
                BlockEvents.INSTANCES.put(eventId.toString(), new Json().fromJson(BlockEvents.class, event.toJson().toString()));
                progress.value++;
                LogWrapper.info("%s: Registered Block Event: %s".formatted(GameLoader.TAG, event.toJson().get("stringId")));
            });
        }
    }

}
