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
import dev.crmodders.flux.api.gui.interfaces.GameStateCache;
import dev.crmodders.flux.api.resource.ResourceObject;
import dev.crmodders.flux.loading.GameLoader;
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
public class RegistryRegisterer {

    @Inject(method = "create", at = @At("TAIL"))
    private void create(CallbackInfo ci) {
        FluxConstants.GameHasLoaded = true;
        try {
            FindLanguages();
            GameEvents.ON_REGISTER_LANGUAGE.invoker().onRegisterLanguage();
            TranslationApi.registerLanguages();
            TranslationApi.setLanguage(FluxSettings.LanguageSetting.getValue());
        } catch (Exception ignored) {}

        GameState.switchToGameState(new GameLoader());
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


}
