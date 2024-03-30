package dev.crmodders.flux.localization;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.tags.Identifier;

import java.util.List;

public class TranslationKey {

    private final Identifier identifier;

    private final int hashCode;

    public TranslationKey(String key) {
        this(Identifier.fromString(key));
    }

    public TranslationKey(Identifier identifier) {
        this.identifier = identifier;
        this.hashCode = identifier.toString().hashCode();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TranslationKey key && key.identifier.equals(identifier);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public TranslationString getTranslated() {
        return FluxSettings.SelectedLanguage.getTranslatedString(this);
    }

    public List<TranslationString> getTranslatedList() {
        return FluxSettings.SelectedLanguage.getTranslatedStrings(this);
    }

    @Override
    public String toString() {
        return identifier.toString();
    }
}
