package dev.crmodders.flux.loading.stages.legacy;

import dev.crmodders.flux.api.v2.blocks.ModBlock;
import dev.crmodders.flux.api.v2.registries.BuiltInRegistries;
import dev.crmodders.flux.api.v5.block.IModBlock;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blocks.Block;

import static dev.crmodders.flux.api.v2.registries.BlockReg.getBlockFromBlock;
import static dev.crmodders.flux.api.v2.registries.BlockReg.getBlockFromJson;
import static dev.crmodders.flux.loading.GameLoader.logger;

public class RegisterBlocks extends LoadStage {

    @Override
    public void initialize(GameLoader loader) {
        super.initialize(loader);
        this.title = new TranslationKey("fluxapi:loading_menu.registering_blocks");
    }

    @Override
    public void doStage() {
        super.doStage();
        AccessableRegistry<IModBlock> registryAccess = FluxRegistries.BLOCKS_V5.access();
        loader.progress2.range = registryAccess.getRegisteredNames().length;

        FluxRegistries.BLOCKS_V5.freeze();
        for (Identifier blockId : registryAccess.getRegisteredNames()) {
            IModBlock modBlock = registryAccess.get(blockId);
            FluxRegistries.FACTORY_FINALIZERS.register(
                    blockId,
                    modBlock.getGenerator().GetGeneratorFactory().get(modBlock, blockId)
            );
            loader.progress2.value++;
            logger.info("Registered V5 Block: {}", blockId);
        }

        BuiltInRegistries.MODDED_BLOCKS.freeze();
        for (Identifier blockID : BuiltInRegistries.MODDED_BLOCKS.access().getRegisteredNames()) {
            ModBlock modBlock = BuiltInRegistries.MODDED_BLOCKS.access().get(blockID);
            if (modBlock.block != null) {
                Block.blocksByName.put(blockID.toString(), getBlockFromBlock(blockID, modBlock.block));
            } else {
                Block.blocksByName.put(blockID.toString(), getBlockFromJson(blockID));
            }
            logger.info("Registered V1 Block: {}", blockID);
        }
    }

}
