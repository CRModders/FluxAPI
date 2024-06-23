package dev.crmodders.flux;

import dev.crmodders.flux.localization.Language;
import finalforeach.cosmicreach.settings.BooleanSetting;
import finalforeach.cosmicreach.settings.IntSetting;

/**
 * A Class that stores all Flux Settings
 * @author Mr-Zombii, nanobass
 */
public class FluxSettings {

    public static final IntSetting AntiAliasing = new IntSetting("msaa", 4);
    public static final BooleanSetting EnabledVanillaMods = new BooleanSetting("enableVanillaMods", true);
    public static Language SelectedLanguage;

}
