package dev.crmodders.flux.localization;

import java.util.List;

public class Language implements Translation {
    private final LanguageFile locale;

    public Language(LanguageFile locale) {
        this.locale = locale;
    }

    private TranslationEntry getEntry(TranslationKey key){
        if(locale.containsKey(key)) {
            return locale.get(key);
        }
        return UNDEFINED;
    }

    @Override
    public TranslationString getTranslatedString(TranslationKey key) {
        return getEntry(key).getString();
    }

    @Override
    public List<TranslationString> getTranslatedStrings(TranslationKey key) {
        return getEntry(key).getStrings();
    }

    @Override
    public TranslationString getTranslatedString(TranslationKey key, int index) {
        return getEntry(key).getString(index);
    }
}
