package dev.crmodders.flux.localization;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import dev.crmodders.flux.FluxRegistries;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.localization.files.MergedLanguageFile;
import dev.crmodders.flux.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.ui.TranslationParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LanguageManager {

	private static final Logger LOGGER = LoggerFactory.getLogger("Language Manager");

	public static boolean hasLanguageInstalled(TranslationLocale locale) {
		return FluxRegistries.LANGUAGES.access().contains(locale.toIdentifier());
	}

	public static void selectLanguage(TranslationLocale locale) {
		AccessableRegistry<Language> allLanguages = FluxRegistries.LANGUAGES.access();
		Language newLanguage = allLanguages.get(locale.toIdentifier());
		if (newLanguage != null) {
			FluxSettings.SelectedLanguage = newLanguage;
		} else {
            LOGGER.error("Language not found {}", locale);
		}
	}

	public static void updateLabels(Stage stage) {
		for(Actor actor : stage.getActors()) {
			if(actor instanceof Label label && label.getUserObject() instanceof TranslationParameters params) {
				updateLabel(label, params);
			}
		}
	}

	public static void updateLabel(Label label, TranslationParameters parameters) {
		if(parameters.attachedProgressBar != null) {
			ProgressBar bar = parameters.attachedProgressBar;
			String text;
			if(bar.getStepSize() == 1.0F) {
				text = format(parameters.key, (int) bar.getValue(), (int) bar.getMaxValue());
			} else {
				text = format(parameters.key, bar.getValue(), bar.getMaxValue());
			}
			label.setText(text);
			label.invalidate();
		} else {
			label.setText(string(parameters.key));
			label.invalidate();
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

	public static TranslationEntry UNDEFINED = new TranslationEntry();

	public static TranslationEntry translate(TranslationKey key) {
		if(FluxSettings.SelectedLanguage == null) {
			if(key == null) {
				return UNDEFINED;
			}
			return new TranslationEntry(key.getIdentifier());
		} else {
			return FluxSettings.SelectedLanguage.entry(key);
		}
	}

	public static String string(TranslationKey key) {
		return translate(key).string().string();
	}

	public static List<TranslationString> strings(TranslationKey key) {
		return translate(key).strings();
	}

	public static String format(TranslationKey key, Object... args) {
		return translate(key).string().format(args);
	}

}
