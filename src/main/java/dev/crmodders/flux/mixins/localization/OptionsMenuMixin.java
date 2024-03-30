package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.localization.TranslationKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import finalforeach.cosmicreach.gamestates.OptionsMenu;

@Mixin(value = OptionsMenu.class, priority = 2000)
public class OptionsMenuMixin {

	@Unique
	private static final TranslationKey TEXT_KEYBINDS = new TranslationKey("options_menu.keybinds");

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 0), index = 0)
	private String keybinds(String text) {
		return TEXT_KEYBINDS.getTranslated().string();
	}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 1), index = 0)
	private String done(String text) {
		return FluxConstants.TextDone.getTranslated().string();
	}

}
