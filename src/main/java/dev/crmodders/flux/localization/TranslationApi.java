package dev.crmodders.flux.localization;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.io.SaveLocation;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TranslationApi {

    public static final File LANGUAGE_FOLDER = new File(SaveLocation.getSaveFolderLocation(), "mods/assets/languages");

    static {
        if(!LANGUAGE_FOLDER.exists())
            LANGUAGE_FOLDER.mkdirs();
    }

    public static final Locale LOCALE_EN_US = Locale.forLanguageTag("en-US");

    public static Identifier getLocaleIdentifier(Locale locale) {
        return new Identifier(FluxConstants.MOD_ID, locale.toLanguageTag());
    }

    public static void registerLanguageFile(LanguageFile file) {
        Identifier identifier = getLocaleIdentifier(file.getLocale());
        AccessableRegistry<LanguageFile> languageFiles = ((AccessableRegistry<LanguageFile>) FluxRegistries.LANGUAGE_FILES);
        if(languageFiles.contains(identifier)) {
            LanguageFile existing = languageFiles.get(identifier);
            existing.merge(file);
        } else {
            FluxRegistries.LANGUAGE_FILES.register(identifier, file);
        }
    }

    public static void registerLanguages() {
        AccessableRegistry<LanguageFile> languageFiles = ((AccessableRegistry<LanguageFile>) FluxRegistries.LANGUAGE_FILES);
        LanguageFile enUs = languageFiles.get(getLocaleIdentifier(LOCALE_EN_US));
        for(Identifier identifier : languageFiles.getRegisteredNames()) {
            LanguageFile file = languageFiles.get(identifier);
            FluxRegistries.LANGUAGES.register(identifier, new Language(file, enUs));
            LogWrapper.info("LanguageFile registered: {} ({})", file.getLocale().getDisplayName(Locale.ENGLISH), file.getLocale().toLanguageTag());
        }
    }

    public static List<Locale> getLanguages() {
        return Arrays.stream(((AccessableRegistry<Language>) FluxRegistries.LANGUAGES).getRegisteredNames()).map(id -> Locale.forLanguageTag(id.name)).toList();
    }

    public static void setLanguage(Locale locale){
        Language old = FluxSettings.SelectedLanguage;
        FluxSettings.SelectedLanguage = ((AccessableRegistry<Language>) FluxRegistries.LANGUAGES).get(getLocaleIdentifier(locale));
        if(FluxSettings.SelectedLanguage == null) {
            FluxSettings.SelectedLanguage = old;
        }
        if(FluxSettings.SelectedLanguage == null) {
            FluxSettings.SelectedLanguage = ((AccessableRegistry<Language>) FluxRegistries.LANGUAGES).get(getLocaleIdentifier(LOCALE_EN_US));
        }
    }
}
