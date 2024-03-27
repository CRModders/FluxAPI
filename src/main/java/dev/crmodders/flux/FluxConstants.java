package dev.crmodders.flux;

import dev.crmodders.flux.api.resource.ResourceLocation;
import finalforeach.cosmicreach.settings.BooleanSetting;
import finalforeach.cosmicreach.settings.IntSetting;

public class FluxConstants {
    public static boolean GameHasLoaded;

    public static final String MOD_ID = "fluxapi";
    public static final String ASSET_KEY = "¬¬$^$^¬¬";

    public static final ResourceLocation FontFile = new ResourceLocation(MOD_ID, "comic_sans_ms.ttf");
    public static final IntSetting antiAliasing = new IntSetting("msaa", 4);
    public static final BooleanSetting replaceFontRenderer = new BooleanSetting("replaceFontRenderer", false);

}
