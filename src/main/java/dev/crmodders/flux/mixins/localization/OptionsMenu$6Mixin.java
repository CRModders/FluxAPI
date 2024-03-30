package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.localization.TranslationKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.ui.UISlider;

@Mixin(targets = "finalforeach.cosmicreach.gamestates.OptionsMenu$6", priority = 2000)
public abstract class OptionsMenu$6Mixin extends UISlider {

	public OptionsMenu$6Mixin(OptionsMenu this0, float min, float max, float defaultVal, float x, float y, float w, float h) {
		super(min, max, defaultVal, x, y, w, h);
	}

	@Unique
	private static final TranslationKey TEXT_FOV = new TranslationKey("options_menu.fov");

	@Override
	public void updateText() {
		setText(TEXT_FOV.getTranslated().format(String.valueOf((int)currentValue)));
	}

}
