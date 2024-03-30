package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.settings.GraphicsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.settings.ControlSettings;
import finalforeach.cosmicreach.ui.UIElement;

@Mixin(targets = "finalforeach.cosmicreach.gamestates.OptionsMenu$3", priority = 2000)
public abstract class OptionsMenu$3Mixin extends UIElement {

	public OptionsMenu$3Mixin(OptionsMenu this0, float x, float y, float w, float h) {
		super(x, y, w, h);
	}

	@Unique
	private static final TranslationKey TEXT_INVERT_MOUSE = new TranslationKey("options_menu.inverted_mouse");

	@Override
	public void updateText() {
		String on = FluxConstants.TextOn.getTranslated().string();
		String off = FluxConstants.TextOff.getTranslated().string();
		setText(TEXT_INVERT_MOUSE.getTranslated().format(ControlSettings.invertedMouse.getValue() ? on : off));
	}

}
