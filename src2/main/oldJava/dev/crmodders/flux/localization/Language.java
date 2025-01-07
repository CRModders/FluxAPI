package dev.crmodders.flux.localization;

import dev.crmodders.flux.FluxRegistries;
import dev.crmodders.flux.registries.AccessableRegistry;

import java.util.List;

public class Language {

	private final ILanguageFile file;

	public Language(ILanguageFile file) {
		super();
		this.file = file;
	}

	protected TranslationEntry entry(TranslationKey key) {
		if (file.contains(key)) {
			return file.get(key);
		}
		for (TranslationLocale fallback : file.fallbacks()) {
			AccessableRegistry<Language> allLanguages = FluxRegistries.LANGUAGES.access();
			Language language = allLanguages.get(fallback.toIdentifier());
			if (language != null) {
				return language.entry(key);
			}
		}
		return new TranslationEntry(key.getIdentifier());
	}

	public TranslationString get(String key) {
		return entry(new TranslationKey(key)).string();
	}

	public String string(String key) {
		return get(key).string();
	}

	public String format(String key, Object... args) {
		return get(key).format(args);
	}

	public TranslationString get(TranslationKey key) {
		return entry(key).string();
	}

	public List<TranslationString> getList(TranslationKey key) {
		return entry(key).strings();
	}

	public TranslationString get(TranslationKey key, int index) {
		return entry(key).string(index);
	}

	public ILanguageFile getFile() {
		return file;
	}

}
