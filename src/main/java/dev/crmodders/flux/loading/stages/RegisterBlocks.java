package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.gui.ProgressBarElement;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;

import java.util.concurrent.ExecutorService;

public class RegisterBlocks implements LoadStage {
    @Override
    public TranslationKey title() {
        return new TranslationKey("fluxapi:loading_menu.registering_blocks");
    }

    @Override
    public void doStage(ProgressBarElement progress, ExecutorService threadPool, ExecutorService glThread) {
        AccessableRegistry<IModBlock> registryAccess = FluxRegistries.BLOCKS.access();
        progress.range = registryAccess.getRegisteredNames().length;

        FluxRegistries.BLOCKS.freeze();
        for (Identifier blockId : registryAccess.getRegisteredNames()) {
            IModBlock modBlock = registryAccess.get(blockId);
            glThread.submit(() -> {
                FluxRegistries.FACTORY_FINALIZERS.register(
                        blockId,
                        modBlock.getGenerator().GetGeneratorFactory().get(modBlock, blockId)
                );
                progress.value++;
                LogWrapper.info("%s: Registered Block: %s".formatted(GameLoader.TAG, blockId));
            });
        }
    }

}
