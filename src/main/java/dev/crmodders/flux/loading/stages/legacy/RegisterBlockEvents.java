package dev.crmodders.flux.loading.stages.legacy;

import dev.crmodders.flux.api.v5.generators.BlockEventGenerator;
import dev.crmodders.flux.api.v5.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blockevents.BlockEvents;

import static dev.crmodders.flux.loading.GameLoader.logger;

public class RegisterBlockEvents extends LoadStage {
    @Override
    public void initialize(GameLoader loader) {
        super.initialize(loader);
        this.title = new TranslationKey("fluxapi:loading_menu.registering_block_events");
    }

    @Override
    public void doStage() {
        super.doStage();
        AccessableRegistry<BlockEventDataExt> registryAccess = FluxRegistries.BLOCK_EVENTS.access();
        loader.progress2.range = registryAccess.getRegisteredNames().length;

        FluxRegistries.BLOCK_EVENTS.freeze();
        for (Identifier eventId : registryAccess.getRegisteredNames()) {
            BlockEventDataExt event = registryAccess.get(eventId);
            BlockEvents events = BlockEventGenerator.fromJson(event.toJson());
            BlockEvents.INSTANCES.put(eventId.toString(), events);
            loader.progress2.value++;
            logger.info("Registered Block Event {}", eventId);
        }
    }

}
