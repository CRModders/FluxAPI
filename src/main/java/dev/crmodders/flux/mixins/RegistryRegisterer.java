package dev.crmodders.flux.mixins;

import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.api.resource.ResourceObject;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.world.blockevents.BlockEvents;
import finalforeach.cosmicreach.world.blockevents.IBlockEventAction;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.registry.ExperimentalRegistries;
import dev.crmodders.flux.registry.StableRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockGame.class)
public class RegistryRegisterer {

    @Inject(method = "create", at = @At("TAIL"))
    private void create(CallbackInfo ci) {
        FluxConstants.GameHasLoaded = true;

        RegisterAssets((AccessableRegistry<ResourceObject>) ExperimentalRegistries.ResourceRegistry);

        StableRegistries.BLOCKS.freeze();
        RegisterBlocks((AccessableRegistry<IModBlock>) StableRegistries.BLOCKS);

        StableRegistries.BLOCK_EVENT_ACTIONS.freeze();
        RegisterBlockEventActions((AccessableRegistry<IBlockEventAction>) StableRegistries.BLOCK_EVENT_ACTIONS);

        StableRegistries.BLOCK_EVENTS.freeze();
        RegisterBlockEvents((AccessableRegistry<BlockEventDataExt>) StableRegistries.BLOCK_EVENTS);

        RegisterBlockFinalizers((AccessableRegistry<BlockGenerator.FactoryFinalizer>) ExperimentalRegistries.FactoryFinalizers);

    }

    private static void RegisterAssets(AccessableRegistry<ResourceObject> registryAccess) {
        for (Identifier resourceId : registryAccess.getRegisteredNames()) {
            ResourceObject resource = registryAccess.get(resourceId);

            if (resource.handle == null)
                resource.handle = GameAssetLoader.loadAsset(
                        resourceId.namespace
                                + FluxConstants.ASSET_KEY
                                + resourceId.name
                );

            FluxConstants.LOGGER.info("{ Registry }: Registered Asset: %s".formatted(resourceId));
        }
    }

    private static void RegisterBlockEventActions(AccessableRegistry<IBlockEventAction> registryAccess) {
        for (Identifier actionId : registryAccess.getRegisteredNames()) {
            IBlockEventAction action = registryAccess.get(actionId);

            BlockEvents.registerBlockEventAction(action);
            FluxConstants.LOGGER.info("{ Registry }: Registered Block Event Action: %s".formatted(action.getActionId()));
        }
    }

    private static void RegisterBlockEvents(AccessableRegistry<BlockEventDataExt> registryAccess) {
        for (Identifier eventId : registryAccess.getRegisteredNames()) {
            BlockEventDataExt event = registryAccess.get(eventId);

            BlockEvents.INSTANCES.put(eventId.toString(), new Json().fromJson(BlockEvents.class, event.toJson().toString()));
            FluxConstants.LOGGER.info("{ Registry }: Registered Block Event: %s".formatted(event));
        }
    }

    private static void RegisterBlocks(AccessableRegistry<IModBlock> registryAccess) {
        for (Identifier blockId : registryAccess.getRegisteredNames()) {
            IModBlock modBlock = registryAccess.get(blockId);

            ExperimentalRegistries.FactoryFinalizers.register(
                    blockId,
                    modBlock.getGenerator().GetGeneratorFactory().get(modBlock, blockId)
            );
            FluxConstants.LOGGER.info("{ Registry }: Registered Block: %s".formatted(blockId));
        }
    }

    private static void RegisterBlockFinalizers(AccessableRegistry<BlockGenerator.FactoryFinalizer> registryAccess) {
        for (Identifier finalizerId : registryAccess.getRegisteredNames()) {
            BlockGenerator.FactoryFinalizer finalizer = registryAccess.get(finalizerId);

            finalizer.finalizeFactory();
            FluxConstants.LOGGER.info("{ Registry }: Registered Block Finalizer: %s".formatted(finalizerId));
        }
    }

}
