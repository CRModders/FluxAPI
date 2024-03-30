package dev.crmodders.flux;

import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.api.settings.LocaleSetting;
import dev.crmodders.flux.localization.Language;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.settings.BooleanSetting;
import finalforeach.cosmicreach.settings.IntSetting;

import java.util.Locale;

public class FluxConstants {
    public static boolean GameHasLoaded;

    public static final String MOD_ID = "fluxapi";
    public static final String ASSET_KEY = "¬¬$^$^¬¬";

    public static final ResourceLocation LanguageEnUs = new ResourceLocation(MOD_ID, "languages/en-US.json");
    public static final ResourceLocation FontFile = new ResourceLocation(MOD_ID, "fonts/cosmic_reach.fnt");
    public static final ResourceLocation WhitePixel = new ResourceLocation(MOD_ID, "whitepixel.png");

    public static final TranslationKey TextOn = new TranslationKey("base:menu.on");
    public static final TranslationKey TextOff = new TranslationKey("base:menu.off");
    public static final TranslationKey TextCancel = new TranslationKey("base:menu.cancel");
    public static final TranslationKey TextBack = new TranslationKey("base:menu.back");
    public static final TranslationKey TextSave = new TranslationKey("base:menu.save");
    public static final TranslationKey TextOk = new TranslationKey("base:menu.ok");
    public static final TranslationKey TextDone = new TranslationKey("base:menu.done");
    public static final TranslationKey TextReturnToMain = new TranslationKey("base:menu.return_to_main_menu");
    public static final TranslationKey TextReturnToGame = new TranslationKey("base:menu.return_to_game");



}
