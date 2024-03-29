package dev.crmodders.flux;

import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.api.settings.LocaleSetting;
import dev.crmodders.flux.localization.Language;
import dev.crmodders.flux.localization.LanguageFile;
import finalforeach.cosmicreach.settings.BooleanSetting;
import finalforeach.cosmicreach.settings.IntSetting;

import java.util.Locale;

public class FluxConstants {
    public static boolean GameHasLoaded;

    public static final String MOD_ID = "fluxapi";
    public static final String ASSET_KEY = "¬¬$^$^¬¬";

    public static final ResourceLocation FontFile = new ResourceLocation(MOD_ID, "comic_sans_ms.ttf");

    public static final IntSetting AntiAliasing = new IntSetting("msaa", 4);
    public static final BooleanSetting ReplaceFontRenderer = new BooleanSetting("replaceFontRenderer", false);
    public static final LocaleSetting LanguageSetting = new LocaleSetting("language", Locale.forLanguageTag("en-US"));

}
