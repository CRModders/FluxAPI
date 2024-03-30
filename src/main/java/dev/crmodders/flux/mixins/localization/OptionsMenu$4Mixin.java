package dev.crmodders.flux.mixins.localization;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import dev.crmodders.flux.localization.TranslationKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.ui.UISlider;

@Mixin(targets = "finalforeach.cosmicreach.gamestates.OptionsMenu$4", priority = 2000)
public abstract class OptionsMenu$4Mixin extends UISlider {

	@Shadow
	NumberFormat format;

	public OptionsMenu$4Mixin(OptionsMenu this0, float min, float max, float defaultVal, float x, float y, float w, float h) {
		super(min, max, defaultVal, x, y, w, h);
	}

	@Unique
	private static final TranslationKey TEXT_MOUSE_SENSITIVITY = new TranslationKey("options_menu.mouse_sensitivity");

	@Override
	public void updateText() {
		if (this.format == null) {
			this.format = DecimalFormat.getPercentInstance();
		}
		String number = this.format.format(this.currentValue);
		setText(TEXT_MOUSE_SENSITIVITY.getTranslated().format(number));
	}

}
