package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.settings.GraphicsSettings;
import finalforeach.cosmicreach.ui.UIElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(targets = "finalforeach.cosmicreach.gamestates.OptionsMenu$1", priority = 2000)
public abstract class OptionsMenu$1Mixin extends UIElement {

	public OptionsMenu$1Mixin(OptionsMenu this0, float x, float y, float w, float h) {
		super(x, y, w, h);
	}

	@Unique
	private static final TranslationKey TEXT_VSYNC = new TranslationKey("options_menu.vsync");

	@Override
	public void updateText() {
		String on = FluxConstants.TextOn.getTranslated().string();
		String off = FluxConstants.TextOff.getTranslated().string();
		setText(TEXT_VSYNC.getTranslated().format(GraphicsSettings.vSyncEnabled.getValue() ? on : off));
	}

}
