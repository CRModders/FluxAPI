package dev.crmodders.flux.mixins.registry;

import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.api.resource.ResourceObject;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.menus.AssetLoadingMenu;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
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

    private static String TAG = "\u001B[35;1m{Registry}\u001B[0m\u001B[37m";

    @Inject(method = "create", at = @At("TAIL"))
    private void create(CallbackInfo ci) {
        FluxConstants.GameHasLoaded = true;
        TranslationApi.discoverLanguages();

        GameState.switchToGameState(new AssetLoadingMenu());

        RegisterAssets((AccessableRegistry<ResourceObject>) ExperimentalRegistries.ResourceRegistry);

        StableRegistries.BLOCKS.freeze();
        RegisterBlocks((AccessableRegistry<IModBlock>) StableRegistries.BLOCKS);

        StableRegistries.BLOCK_EVENT_ACTIONS.freeze();
        RegisterBlockEventActions((AccessableRegistry<IBlockEventAction>) StableRegistries.BLOCK_EVENT_ACTIONS);

        StableRegistries.BLOCK_EVENTS.freeze();
        RegisterBlockEvents((AccessableRegistry<BlockEventDataExt>) StableRegistries.BLOCK_EVENTS);

        RegisterBlockFinalizers((AccessableRegistry<BlockGenerator.FactoryFinalizer>) StableRegistries.BLOCK_FACTORY_FINALIZERS);

        ((AssetLoadingMenu) GameState.currentGameState).regenerate();

    }

    private static void RegisterAssets(AccessableRegistry<ResourceObject> registryAccess) {
        for (Identifier resourceId : registryAccess.getRegisteredNames()) {
            ResourceObject resource = registryAccess.get(resourceId);

            if (resource.handle == null)
                resource.handle = GameAssetLoader.loadAsset(resource.toString());

            LogWrapper.info("%s: Registered Asset: %s".formatted(TAG, resourceId));
        }
    }

    private static void RegisterBlockEventActions(AccessableRegistry<IBlockEventAction> registryAccess) {
        for (Identifier actionId : registryAccess.getRegisteredNames()) {
            IBlockEventAction action = registryAccess.get(actionId);

            BlockEvents.registerBlockEventAction(action);
            LogWrapper.info("%s: Registered Block Event Action %s".formatted(TAG, action.getActionId()));
        }
    }

    private static void RegisterBlockEvents(AccessableRegistry<BlockEventDataExt> registryAccess) {
        for (Identifier eventId : registryAccess.getRegisteredNames()) {
            BlockEventDataExt event = registryAccess.get(eventId);

            BlockEvents.INSTANCES.put(eventId.toString(), new Json().fromJson(BlockEvents.class, event.toJson().toString()));
            LogWrapper.info("%s: Registered Block Event: %s".formatted(TAG, event));
        }
    }

    private static void RegisterBlocks(AccessableRegistry<IModBlock> registryAccess) {
        for (Identifier blockId : registryAccess.getRegisteredNames()) {
            IModBlock modBlock = registryAccess.get(blockId);

            StableRegistries.BLOCK_FACTORY_FINALIZERS.register(
                    blockId,
                    modBlock.getGenerator().GetGeneratorFactory().get(modBlock, blockId)
            );
            LogWrapper.info("%s: Registered Block: %s".formatted(TAG, blockId));
        }
    }

    private static void RegisterBlockFinalizers(AccessableRegistry<BlockGenerator.FactoryFinalizer> registryAccess) {
        for (Identifier finalizerId : registryAccess.getRegisteredNames()) {
            BlockGenerator.FactoryFinalizer finalizer = registryAccess.get(finalizerId);

            finalizer.finalizeFactory();
            LogWrapper.info("%s: Registered Block Finalizer: %s".formatted(TAG, finalizerId));
        }
    }

}
