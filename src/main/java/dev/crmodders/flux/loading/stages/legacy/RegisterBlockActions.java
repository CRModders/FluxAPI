package dev.crmodders.flux.loading.stages.legacy;

import dev.crmodders.flux.annotations.LegacyInternal;
import dev.crmodders.flux.api.v2.registries.BuiltInRegistries;
import dev.crmodders.flux.api.v5.generators.BlockEventGenerator;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;

import static dev.crmodders.flux.loading.GameLoader.logger;

@LegacyInternal
public class RegisterBlockActions extends LoadStage {

    @Override
    public void initialize(GameLoader loader) {
        super.initialize(loader);
        this.title = new TranslationKey("fluxapi:loading_menu.registering_block_event_actions");
    }

    @Override
    public void doStage() {
        super.doStage();
        AccessableRegistry<IBlockAction> registryAccess = FluxRegistries.BLOCK_EVENT_ACTIONS.access();
        loader.progress2.range = registryAccess.getRegisteredNames().length;

        FluxRegistries.BLOCK_EVENT_ACTIONS.freeze();
        for (Identifier actionId : registryAccess.getRegisteredNames()) {
            IBlockAction action = registryAccess.get(actionId);
            BlockEventGenerator.registerBlockEventAction(actionId, action);
            loader.progress2.value++;
            logger.info("Registered Block Event Action {}", actionId);

        }

        BuiltInRegistries.MODDED_BLOCK_EVENTS.freeze();
        for (Identifier eventID : BuiltInRegistries.MODDED_BLOCK_EVENTS.access().getRegisteredNames()) {
            BlockEvents event = BuiltInRegistries.MODDED_BLOCK_EVENTS.access().get(eventID);
            BlockEvents.INSTANCES.put(eventID.toString(), event);
        }
    }

}
