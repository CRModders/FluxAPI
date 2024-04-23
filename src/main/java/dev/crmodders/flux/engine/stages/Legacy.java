package dev.crmodders.flux.engine.stages;

import dev.crmodders.flux.api.v5.block.IModBlock;
import dev.crmodders.flux.api.v5.generators.BlockEventGenerator;
import dev.crmodders.flux.api.v5.generators.FactoryFinalizer;
import dev.crmodders.flux.api.v5.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.engine.GameLoader;
import dev.crmodders.flux.engine.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;

import java.util.List;

public class Legacy extends LoadStage {

    @Override
    public void initialize(GameLoader loader) {
        super.initialize(loader);
        this.title = new TranslationKey("fluxapi:loading_menu.legacy");
    }

    @Override
    public void doStage() {
        super.doStage();

        FluxRegistries.BLOCKS_V5.freeze();
        FluxRegistries.BLOCK_EVENT_ACTIONS.freeze();
        FluxRegistries.BLOCK_EVENTS.freeze();

        AccessableRegistry<IModBlock> modBlocks = FluxRegistries.BLOCKS_V5.access();
        Identifier[] modBlockIds = modBlocks.getRegisteredNames();
        loader.setupProgressBar(loader.progress2, modBlockIds.length, "Registering Blocks");
        for (Identifier blockId : modBlockIds) {
            IModBlock modBlock = modBlocks.get(blockId);
            FluxRegistries.FACTORY_FINALIZERS.register(
                    blockId,
                    modBlock.getGenerator().GetGeneratorFactory().apply(modBlock, blockId)
            );
            loader.incrementProgress(loader.progress2);
        }

        AccessableRegistry<IBlockAction> blockActions = FluxRegistries.BLOCK_EVENT_ACTIONS.access();
        Identifier[] blockActionIds = blockActions.getRegisteredNames();
        loader.setupProgressBar(loader.progress2, blockActionIds.length, "Registering Block Actions");
        for (Identifier actionId : blockActionIds) {
            IBlockAction action = blockActions.get(actionId);
            BlockEventGenerator.registerBlockEventAction(actionId, action);
            loader.incrementProgress(loader.progress2);
        }

        AccessableRegistry<BlockEventDataExt> blockEvents = FluxRegistries.BLOCK_EVENTS.access();
        Identifier[] blockEventIds = blockEvents.getRegisteredNames();
        loader.setupProgressBar(loader.progress2, blockEventIds.length, "Registering Block Events");
        for (Identifier eventId : blockEventIds) {
            BlockEventDataExt event = blockEvents.get(eventId);
            BlockEvents events = BlockEventGenerator.fromJson(event.toJson());
            BlockEvents.INSTANCES.put(eventId.toString(), events);
            loader.incrementProgress(loader.progress2);
        }

    }

    @Override
    public List<Runnable> getGlTasks() {
        List<Runnable> tasks = super.getGlTasks();
        AccessableRegistry<FactoryFinalizer<?>> finalizers = FluxRegistries.FACTORY_FINALIZERS.access();
        Identifier[] finalizerIds = finalizers.getRegisteredNames();
        tasks.add( () -> loader.setupProgressBar(loader.progress2, finalizerIds.length, "Finalizing Blocks") );
        for (Identifier finalizerId : finalizers.getRegisteredNames()) {
            FactoryFinalizer<?> finalizer = finalizers.get(finalizerId);
            tasks.add( finalizer::finalizeFactory );
            tasks.add( () -> loader.incrementProgress(loader.progress2) );
        }
        return tasks;
    }
}
