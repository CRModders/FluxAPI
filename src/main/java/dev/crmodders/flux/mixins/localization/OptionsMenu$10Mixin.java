package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.UISlider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(targets = "finalforeach.cosmicreach.gamestates.OptionsMenu$10", priority = 2000)
public abstract class OptionsMenu$10Mixin extends UIElement {

	public OptionsMenu$10Mixin(OptionsMenu this0, float x, float y, float w, float h) {
		super(x, y, w, h);
	}

	@Unique
	private static final TranslationKey TEXT_CRASH = new TranslationKey("options_menu.crash_the_game");

	@Override
	public void updateText() {
		setText(TEXT_CRASH.getTranslated().string());
	}

}
