package dev.crmodders.flux.localization;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.tags.Identifier;

import java.util.Locale;
import java.util.Objects;

public class TranslationLocale {

	public static TranslationLocale fromLanguageTag(String languageTag) {
		Locale locale = Locale.forLanguageTag(languageTag);
		return new TranslationLocale(locale.getCountry(), locale.getDisplayCountry(), locale.getLanguage(), locale.getDisplayLanguage(), locale.getVariant(), locale.getDisplayVariant());
	}

	public String country, language, variant;
	public String countryName, languageName, variantName;

	public TranslationLocale(String country, String countryName, String language, String languageName, String variant, String variantName) {
		super();
		this.country = country;
		this.language = language;
		this.variant = variant;
		this.countryName = countryName;
		this.languageName = languageName;
		this.variantName = variantName;
	}

	public String toLanguageTag() {
		return new Locale(language, country, variant).toLanguageTag();
	}

	public Identifier toIdentifier() {
		return new Identifier(FluxConstants.MOD_ID, toLanguageTag());
	}

	public String getDisplayName() {
		if (variant == null) {
			return "%s (%s)".formatted(languageName, countryName);
		}

		return "%s (%s, %s)".formatted(languageName, variantName, countryName);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof TranslationLocale locale && Objects.equals(locale.country, this.country) && Objects.equals(locale.language, this.language) && Objects.equals(locale.variant, this.variant);
	}
	
	@Override
	public int hashCode() {
		if(variant == null) {
			return country.hashCode() + language.hashCode();
		}
		return country.hashCode() + language.hashCode() + variant.hashCode();
	}
	
	@Override
	public String toString() {
		return toLanguageTag();
	}

}
