package dev.crmodders.flux.localization;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.registry.ExperimentalRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameAssetLoader;
import jdk.jfr.Experimental;

import java.io.IOException;
import java.util.*;

public class TranslationApi {
    public static Identifier getLocaleIdentifier(Locale locale) {
        return new Identifier(FluxConstants.MOD_ID, locale.toString().replace("_","-"));
    }

    public static final Locale LOCALE_EN_US = Locale.forLanguageTag("en-US");
    public static final Identifier EN_US = getLocaleIdentifier(LOCALE_EN_US);

    public static void discoverLanguages() {
        try {
            registerLanguage(LanguageFile.loadLanguageFile(GameAssetLoader.loadAsset("fluxapi:language/en-US.json")));
            setLanguage(LOCALE_EN_US);
            // TODO: discord languages form classpath and mods folder
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerLanguage(LanguageFile file) {
        Identifier identifier = getLocaleIdentifier(file.getLocale());
        ExperimentalRegistries.LanguageFiles.register(identifier, file);
        LanguageFile en_US = ((AccessableRegistry<LanguageFile>) ExperimentalRegistries.LanguageFiles).get(EN_US);
        ExperimentalRegistries.Languages.register(identifier, new Language(en_US, file));
    }

    public static List<Locale> getLanguages() {
        return Arrays.stream(((AccessableRegistry<Language>) ExperimentalRegistries.Languages).getRegisteredNames()).map(id -> Locale.forLanguageTag(id.name)).toList();
    }

    public static void setLanguage(Locale locale){
        FluxSettings.SelectedLanguage = ((AccessableRegistry<Language>) ExperimentalRegistries.Languages).get(getLocaleIdentifier(locale));
    }

}
