package dev.crmodders.flux.mixins.registry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.events.GameEvents;
import dev.crmodders.flux.api.generators.FactoryFinalizer;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.resource.ResourceObject;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.menus.AssetLoadingMenu;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blockevents.IBlockEventAction;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import finalforeach.cosmicreach.gamestates.WorldSelectionMenu;
import finalforeach.cosmicreach.settings.SoundSettings;
import finalforeach.cosmicreach.ui.UIElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Mixin(BlockGame.class)
@SuppressWarnings("unchecked")
public class RegistryRegisterer {
    private static final String TAG = "\u001B[35;1m{Registry}\u001B[0m\u001B[37m";

    private static final int REQUESTING = 1, LOADING = 2, LOADING2 = 3, FINISHED = 4;

    private AssetLoadingMenu loadingMenu;
    private int blockLoadingStage = 0;

    @Inject(method = "create", at = @At("TAIL"))
    private void create(CallbackInfo ci) {
        FluxConstants.GameHasLoaded = true;
        try {
            FindLanguages();
            GameEvents.ON_REGISTER_LANGUAGE.invoker().onRegisterLanguage();
            TranslationApi.registerLanguages();
            TranslationApi.setLanguage(FluxSettings.LanguageSetting.getValue());
        } catch (Exception ignored) {}

        loadingMenu = new AssetLoadingMenu();
        GameState.switchToGameState(loadingMenu);

        Thread loadingThread = new Thread(this::LoadAssets);
        loadingThread.start();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(CallbackInfo ci) {
        if(GameState.currentGameState != loadingMenu) {
            return;
        }
        // TODO Clean this up
        switch (blockLoadingStage) {
            case REQUESTING:
                blockLoadingStage = LOADING;
                break;
            case LOADING:
                blockLoadingStage = LOADING2;
                break;
            case LOADING2:
                Block.getNumberOfTotalBlockStates();
                blockLoadingStage = FINISHED;
                break;
        }

        Runtime runtime = Runtime.getRuntime();
        loadingMenu.ram.value = (int) ((runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
        loadingMenu.ram.range = (int) (runtime.maxMemory() / (1024 * 1024));

        loadingMenu.ram.updateText();
        loadingMenu.major.updateText();
        loadingMenu.minor.updateText();
        loadingMenu.icon.iconRotation += 1.5f;
        loadingMenu.icon.repaint();
        if(FluxConstants.FluxHasLoaded) {
            if(SoundSettings.isSoundEnabled()) {
                long id = UIElement.onClickSound.play();
                UIElement.onClickSound.setPitch(id, 4f);
            }
            GameState.switchToGameState(GameState.MAIN_MENU);
        }
    }

    private void LoadAssets() {
        loadingMenu.major.range = 8;

        loadingMenu.major.translation = new TranslationKey("fluxapi:loading_menu.loading_cosmic_reach");
        loadingMenu.major.value++;
        blockLoadingStage = REQUESTING;
        while(blockLoadingStage != FINISHED) {
            try {
                Thread.sleep(25);
            } catch (Exception ignored) {}
        }

        loadingMenu.major.translation = new TranslationKey("fluxapi:loading_menu.registering_assets");
        loadingMenu.major.value++;
        RegisterAssets((AccessableRegistry<ResourceObject>) FluxRegistries.GAME_RESOURCES);

        loadingMenu.major.translation = new TranslationKey("fluxapi:loading_menu.initializing");
        loadingMenu.major.value++;
        GameEvents.ON_GAME_INITIALIZED.invoker().onInitialized();

        loadingMenu.major.translation = new TranslationKey("fluxapi:loading_menu.registering_blocks");
        loadingMenu.major.value++;
        FluxRegistries.BLOCKS.freeze();
        RegisterBlocks((AccessableRegistry<IModBlock>) FluxRegistries.BLOCKS);

        loadingMenu.major.translation = new TranslationKey("fluxapi:loading_menu.registering_block_event_actions");
        loadingMenu.major.value++;
        FluxRegistries.BLOCK_EVENT_ACTIONS.freeze();
        RegisterBlockEventActions((AccessableRegistry<IBlockEventAction>) FluxRegistries.BLOCK_EVENT_ACTIONS);

        loadingMenu.major.translation = new TranslationKey("fluxapi:loading_menu.registering_block_events");
        loadingMenu.major.value++;
        FluxRegistries.BLOCK_EVENTS.freeze();
        RegisterBlockEvents((AccessableRegistry<BlockEventDataExt>) FluxRegistries.BLOCK_EVENTS);

        loadingMenu.major.translation = new TranslationKey("fluxapi:loading_menu.finalizing");
        loadingMenu.major.value++;
        RegisterBlockFinalizers((AccessableRegistry<FactoryFinalizer<?>>) FluxRegistries.FACTORY_FINALIZERS);

        loadingMenu.major.translation = new TranslationKey("fluxapi:loading_menu.finished_loading");
        loadingMenu.major.value++;
        FluxConstants.FluxHasLoaded = true;

    }

    private static void FindLanguages() {
        FileHandle folder = Gdx.files.absolute(TranslationApi.LANGUAGE_FOLDER.getAbsolutePath());
        List<FileHandle> found = new ArrayList<>();
        Stack<FileHandle> stack = new Stack<>();
        stack.push(folder);
        while(!stack.isEmpty()) {
            FileHandle pop = stack.pop();
            if(pop.isDirectory()) {
                Collections.addAll(stack, pop.list());
            } else {
                found.add(pop);
            }
        }
        for(FileHandle lang : found) {
            GameEvents.ON_REGISTER_LANGUAGE.register(() -> {
                TranslationApi.registerLanguageFile(LanguageFile.loadLanguageFile(lang));
            });
        }
    }

    private void RegisterAssets(AccessableRegistry<ResourceObject> registryAccess) {
        loadingMenu.minor.setRange(registryAccess.getRegisteredNames().length);
        for (Identifier resourceId : registryAccess.getRegisteredNames()) {
            ResourceObject resource = registryAccess.get(resourceId);

            if (resource.handle == null)
                resource.handle = GameAssetLoader.loadAsset(resource.key.toString());

            loadingMenu.minor.value++;

            LogWrapper.info("%s: Registered Asset: %s".formatted(TAG, resourceId));
        }
        loadingMenu.minor.setRange(0);
    }

    private void RegisterBlockEventActions(AccessableRegistry<IBlockEventAction> registryAccess) {
        loadingMenu.minor.setRange(registryAccess.getRegisteredNames().length);
        for (Identifier actionId : registryAccess.getRegisteredNames()) {
            IBlockEventAction action = registryAccess.get(actionId);

            BlockEvents.registerBlockEventAction(action);

            loadingMenu.minor.value++;

            LogWrapper.info("%s: Registered Block Event Action %s".formatted(TAG, action.getActionId()));
        }
        loadingMenu.minor.setRange(0);
    }

    private void RegisterBlockEvents(AccessableRegistry<BlockEventDataExt> registryAccess) {
        loadingMenu.minor.setRange(registryAccess.getRegisteredNames().length);
        for (Identifier eventId : registryAccess.getRegisteredNames()) {
            BlockEventDataExt event = registryAccess.get(eventId);

            BlockEvents.INSTANCES.put(eventId.toString(), new Json().fromJson(BlockEvents.class, event.toJson().toString()));

            loadingMenu.minor.value++;

            LogWrapper.info("%s: Registered Block Event: %s".formatted(TAG, event.toJson().get("stringId")));
        }
        loadingMenu.minor.setRange(0);
    }

    private void RegisterBlocks(AccessableRegistry<IModBlock> registryAccess) {
        loadingMenu.minor.setRange(registryAccess.getRegisteredNames().length);
        for (Identifier blockId : registryAccess.getRegisteredNames()) {
            IModBlock modBlock = registryAccess.get(blockId);

            FluxRegistries.FACTORY_FINALIZERS.register(
                    blockId,
                    modBlock.getGenerator().GetGeneratorFactory().get(modBlock, blockId)
            );

            loadingMenu.minor.value++;

            LogWrapper.info("%s: Registered Block: %s".formatted(TAG, blockId));
        }
        loadingMenu.minor.setRange(0);
    }

    private void RegisterBlockFinalizers(AccessableRegistry<FactoryFinalizer<?>> registryAccess) {
        loadingMenu.minor.setRange(registryAccess.getRegisteredNames().length);
        for (Identifier finalizerId : registryAccess.getRegisteredNames()) {
            FactoryFinalizer<?> finalizer = registryAccess.get(finalizerId);

            finalizer.finalizeFactory();

            loadingMenu.minor.value++;

            LogWrapper.info("%s: Registered Block Finalizer: %s".formatted(TAG, finalizerId));
        }
        loadingMenu.minor.setRange(0);
    }

}
