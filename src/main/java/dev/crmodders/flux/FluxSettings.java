package dev.crmodders.flux;

import dev.crmodders.flux.api.settings.LocaleSetting;
import dev.crmodders.flux.localization.Language;
import finalforeach.cosmicreach.settings.IntSetting;

import java.util.Locale;

public class FluxSettings {

    public static final IntSetting AntiAliasing = new IntSetting("msaa", 4);
    public static final LocaleSetting LanguageSetting = new LocaleSetting("language", Locale.forLanguageTag("en-US"));
    public static Language SelectedLanguage = null;

}
