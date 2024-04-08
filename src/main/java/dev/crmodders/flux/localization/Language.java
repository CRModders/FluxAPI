package dev.crmodders.flux.localization;

import java.util.List;

public class Language implements Translation {
    private final LanguageFile locale;

    private final LanguageFile enUs;

    public Language(LanguageFile locale, LanguageFile enUs) {
        this.locale = locale;
        this.enUs = enUs;
    }

    private TranslationEntry getEntry(TranslationKey key){
        if(locale.containsKey(key)) {
            return locale.get(key);
        }
        if(enUs.containsKey(key)) {
            return enUs.get(key);
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
