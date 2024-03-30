package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.ui.UISlider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(targets = "finalforeach.cosmicreach.gamestates.OptionsMenu$7", priority = 2000)
public abstract class OptionsMenu$7Mixin extends UISlider {

	public OptionsMenu$7Mixin(OptionsMenu this0, float min, float max, float defaultVal, float x, float y, float w, float h) {
		super(min, max, defaultVal, x, y, w, h);
	}

	@Unique
	private static final TranslationKey TEXT_FPS = new TranslationKey("options_menu.max_fps");

	@Override
	public void updateText() {
		int fps = (int)this.currentValue;
		String fpsStr = "Unlimited";
		if (fps != 250 && fps != 0) {
			fpsStr = String.valueOf(fps);
		}
		setText(TEXT_FPS.getTranslated().format(fpsStr));
	}

}
