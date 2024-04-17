package dev.crmodders.flux;

import dev.crmodders.flux.api.v6.resource.ResourceLocation;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;

/**
 * A Class for all Constants and static Variables used by Flux
 * and Addons
 * @author Mr-Zombii, nanobass
 */
public class FluxConstants {
    /**
     * Is set true when Cosmic Reach has finished loading
     * Set after Phase 1 of loading
     */
    public static boolean GameHasLoaded;

    /**
     * Is set true when FluxAPI has finished loading
     * Set after Phase 7 of loading
     */
    public static boolean FluxHasLoaded;

    public static final String MOD_ID = "fluxapi";
    public static final String ASSET_KEY = "¬¬$^$^¬¬";

    public static GameState MAIN_MENU = new MainMenu();

    public static final ResourceLocation LanguageEnUs = new ResourceLocation(MOD_ID, "languages/en-US.json");
    public static final ResourceLocation WhitePixel = new ResourceLocation(MOD_ID, "whitepixel.png");
    public static ResourceLocation DirectoryIcon = new ResourceLocation(MOD_ID, "folder_icon.png");
    public static ResourceLocation ReloadIcon = new ResourceLocation(MOD_ID, "reload_icon.png");
    public static ResourceLocation CRModdersIcon = new ResourceLocation(MOD_ID, "crmodders.png");

    public static final TranslationKey TextOn = new TranslationKey("fluxapi:menu.on");
    public static final TranslationKey TextOff = new TranslationKey("fluxapi:menu.off");
    public static final TranslationKey TextCancel = new TranslationKey("fluxapi:menu.cancel");
    public static final TranslationKey TextBack = new TranslationKey("fluxapi:menu.back");
    public static final TranslationKey TextSave = new TranslationKey("fluxapi:menu.save");
    public static final TranslationKey TextOk = new TranslationKey("fluxapi:menu.ok");
    public static final TranslationKey TextDone = new TranslationKey("fluxapi:menu.done");
    public static final TranslationKey TextReturnToMainMenu = new TranslationKey("fluxapi:menu.return_to_main_menu");
    public static final TranslationKey TextReturnToGame = new TranslationKey("fluxapi:menu.return_to_game");


}
