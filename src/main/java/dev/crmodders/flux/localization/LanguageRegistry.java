package dev.crmodders.flux.localization;

import com.badlogic.gdx.files.FileHandle;
import finalforeach.cosmicreach.GameAssetLoader;

import java.io.IOException;
import java.util.*;

public class LanguageRegistry {
    public static final Locale EN_US = Locale.forLanguageTag("en-US");
    private static Map<Locale, LanguageFile> languageFiles = new HashMap<>();
    private static Map<Locale, Language> languages = new HashMap<>();
    private static Language language = null;

    public static void discoverLanguages() {
        registerLanguage(GameAssetLoader.loadAsset("fluxapi:language/en-US.json"));
        // TODO: discord languages form classpath and mods folder
    }

    public static void registerLanguage(FileHandle file) {
        try {
            registerLanguage(LanguageFile.loadLanguageFile(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerLanguage(LanguageFile file) {
        languageFiles.put(file.getLocale(), file);
        languages.put(file.getLocale(), new Language(languageFiles.get(EN_US), file));
    }

    public static LanguageFile getLanguageFile(Locale locale) {
        return languageFiles.get(locale);
    }

    public static List<Locale> getLanguages() {
        return new ArrayList<>(languageFiles.keySet());
    }

    public static Language getLanguage(Locale locale) {
        return languages.get(locale);
    }

    public static void setLanguage(Locale locale){
        language = getLanguage(locale);
    }

    public static Language getLanguage(){
        return language;
    }

}
