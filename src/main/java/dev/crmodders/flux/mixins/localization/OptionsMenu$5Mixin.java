package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.settings.SoundSettings;
import finalforeach.cosmicreach.ui.UIElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(targets = "finalforeach.cosmicreach.gamestates.OptionsMenu$5", priority = 2000)
public abstract class OptionsMenu$5Mixin extends UIElement {

	public OptionsMenu$5Mixin(OptionsMenu this0, float x, float y, float w, float h) {
		super(x, y, w, h);
	}

	@Unique
	private static final TranslationKey TEXT_SOUND = new TranslationKey("options_menu.sound");

	@Override
	public void updateText() {
		String on = FluxConstants.TextOn.getTranslated().string();
		String off = FluxConstants.TextOff.getTranslated().string();
		setText(TEXT_SOUND.getTranslated().format(SoundSettings.isSoundEnabled() ? on : off));
	}

}
