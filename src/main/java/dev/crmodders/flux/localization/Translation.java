package dev.crmodders.flux.localization;

import java.util.List;

public interface Translation {

    public static final TranslationEntry UNDEFINED = new TranslationEntry();

    TranslationString getTranslatedString(TranslationKey key);

    List<TranslationString> getTranslatedStrings(TranslationKey key);

    TranslationString getTranslatedString(TranslationKey key, int index);

}
