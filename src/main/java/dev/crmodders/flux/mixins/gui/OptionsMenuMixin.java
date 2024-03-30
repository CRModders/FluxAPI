package dev.crmodders.flux.mixins.gui;

import dev.crmodders.flux.api.gui.SwitchGameStateButtonElement;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.menus.FluxOptionMenu;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.ui.UIElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsMenu.class)
public abstract class OptionsMenuMixin extends GameState {

	private static final TranslationKey TEXT_FLUX_OPTIONS = new TranslationKey("fluxapi:flux_options.name");

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addUiElements(CallbackInfo ci) {
		for (int i = 0; i < uiElements.size; i++) {
			if (uiElements.get(i).x == 0f && uiElements.get(i).y == 100f) {
				UIElement keybinds = uiElements.get(i);
				keybinds.x = -137.0f;
				keybinds.y = 25f;
				break;
			}
		}
		SwitchGameStateButtonElement button = new SwitchGameStateButtonElement(0f, 100f, 250f, 50f, () -> new FluxOptionMenu(this), TEXT_FLUX_OPTIONS);
		button.show();
		uiElements.add(button);
	}

}
