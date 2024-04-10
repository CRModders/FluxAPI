package dev.crmodders.flux.mixins.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.events.GameEvents;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.localization.TranslationApi;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.gamestates.GameState;
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
