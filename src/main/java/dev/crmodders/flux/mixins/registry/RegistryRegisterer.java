package dev.crmodders.flux.mixins.registry;

import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.events.GameEvents;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.generators.FactoryFinalizer;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.resource.ResourceObject;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.menus.AssetLoadingMenu;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.BlockBuilderUtils;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blockevents.IBlockEventAction;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import org.hjson.JsonValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockGame.class)
@SuppressWarnings("unchecked")
public class RegistryRegisterer {

    private static String TAG = "\u001B[35;1m{Registry}\u001B[0m\u001B[37m";

    @Inject(method = "create", at = @At("TAIL"))
    private void create(CallbackInfo ci) {
        FluxConstants.GameHasLoaded = true;
        TranslationApi.discoverLanguages();

        GameState.switchToGameState(new AssetLoadingMenu());

        RegisterAssets((AccessableRegistry<ResourceObject>) FluxRegistries.GAME_RESOURCES);

        GameEvents.ON_GAME_INITIALIZED.invoker().onInitialized();

        FluxRegistries.BLOCKS.freeze();
        RegisterBlocks((AccessableRegistry<IModBlock>) FluxRegistries.BLOCKS);

        FluxRegistries.BLOCK_EVENT_ACTIONS.freeze();
        RegisterBlockEventActions((AccessableRegistry<IBlockEventAction>) FluxRegistries.BLOCK_EVENT_ACTIONS);

        FluxRegistries.BLOCK_EVENTS.freeze();
        RegisterBlockEvents((AccessableRegistry<BlockEventDataExt>) FluxRegistries.BLOCK_EVENTS);

        RegisterBlockFinalizers((AccessableRegistry<FactoryFinalizer<?>>) FluxRegistries.FACTORY_FINALIZERS);

        GameState.switchToGameState(new MainMenu());

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
            LogWrapper.info("%s: Registered Block Event: %s".formatted(TAG, event.toJson().get("stringId")));
        }
    }

    private static void RegisterBlocks(AccessableRegistry<IModBlock> registryAccess) {
        for (Identifier blockId : registryAccess.getRegisteredNames()) {
            IModBlock modBlock = registryAccess.get(blockId);

            FluxRegistries.FACTORY_FINALIZERS.register(
                    blockId,
                    modBlock.getGenerator().GetGeneratorFactory().get(modBlock, blockId)
            );

            LogWrapper.info("%s: Registered Block: %s".formatted(TAG, blockId));
        }
    }

    private static void RegisterBlockFinalizers(AccessableRegistry<FactoryFinalizer<?>> registryAccess) {
        for (Identifier finalizerId : registryAccess.getRegisteredNames()) {
            FactoryFinalizer<?> finalizer = registryAccess.get(finalizerId);

            finalizer.finalizeFactory();
            LogWrapper.info("%s: Registered Block Finalizer: %s".formatted(TAG, finalizerId));
        }
    }

}
