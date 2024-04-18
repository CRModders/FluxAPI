package dev.crmodders.flux.mixins.gui;

import dev.crmodders.flux.api.v6.gui.SwitchGameStateButtonElement;
import dev.crmodders.flux.api.v6.gui.interfaces.GameStateInterface;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.menus.FluxOptionMenu;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.UIObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsMenu.class)
public abstract class OptionsMenuMixin extends GameState {

	private static final TranslationKey TEXT_FLUX_OPTIONS = new TranslationKey("fluxapi:flux_options.name");

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addUiElements(CallbackInfo ci) {
		SwitchGameStateButtonElement button = new SwitchGameStateButtonElement(() -> new FluxOptionMenu(this));
		button.setBounds(-137.0f, 25f, 250f, 50f);
		button.translation = TEXT_FLUX_OPTIONS;
		button.visible = true;
		button.updateText();
		((GameStateInterface)this).getComponents().add(button);
	}

}
