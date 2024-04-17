package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.api.v2.blocks.ModBlock;
import dev.crmodders.flux.api.v2.registries.BlockReg;
import dev.crmodders.flux.api.v2.registries.BuiltInRegistries;
import dev.crmodders.flux.api.v5.block.IModBlock;
import dev.crmodders.flux.api.v5.gui.ProgressBarElement;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blocks.Block;

import java.util.concurrent.ExecutorService;

import static dev.crmodders.flux.api.v2.registries.BlockReg.getBlockFromBlock;
import static dev.crmodders.flux.api.v2.registries.BlockReg.getBlockFromJson;

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

        BuiltInRegistries.MODDED_BLOCKS.freeze();
        for (Identifier blockID : BuiltInRegistries.MODDED_BLOCKS.access().getRegisteredNames()) {
            ModBlock modBlock = BuiltInRegistries.MODDED_BLOCKS.access().get(blockID);
            if (modBlock.block != null) {
                Block.blocksByName.put(blockID.toString(), getBlockFromBlock(blockID, modBlock.block));
            } else {
                Block.blocksByName.put(blockID.toString(), getBlockFromJson(blockID));
            }
            LogWrapper.info("%s: Registered Block: %s".formatted(GameLoader.TAG, blockID));
        }
    }

}
