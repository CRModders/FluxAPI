package dev.crmodders.flux.localization;

import dev.crmodders.flux.FluxRegistries;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.localization.files.MergedLanguageFile;
import dev.crmodders.flux.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.logging.LoggingAgent;
import dev.crmodders.flux.logging.api.MicroLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LanguageManager {

	private static final MicroLogger LOGGER = LoggingAgent.getLogger("Language Manager");

	public static void selectLanguage(TranslationLocale locale) {
		AccessableRegistry<Language> allLanguages = FluxRegistries.LANGUAGES.access();
		Language newLanguage = allLanguages.get(locale.toIdentifier());
		if (newLanguage != null) {
			FluxSettings.SelectedLanguage = newLanguage;
		} else {
            LOGGER.error("Language not found {}", locale);
		}
	}

	public static void registerLanguageFile(ILanguageFile lang) {
		TranslationLocale locale = lang.locale();
		Identifier localeIdentifier = locale.toIdentifier();

		AccessableRegistry<Language> allLanguages = FluxRegistries.LANGUAGES.access();
		AccessableRegistry<ILanguageFile> allLanguageFiles = FluxRegistries.LANGUAGE_FILES.access();

		if (allLanguageFiles.get(localeIdentifier) instanceof MergedLanguageFile merged) {
			merged.addLanguageFile(lang);
		} else {
			MergedLanguageFile merged = new MergedLanguageFile(locale);
			merged.addLanguageFile(lang);
			FluxRegistries.LANGUAGE_FILES.register(localeIdentifier, merged);
		}

		if (!allLanguages.contains(localeIdentifier)) {
			Language language = new Language(allLanguageFiles.get(localeIdentifier));
			FluxRegistries.LANGUAGES.register(localeIdentifier, language);
		}
	}

}
