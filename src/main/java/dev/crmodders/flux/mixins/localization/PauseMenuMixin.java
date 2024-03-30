package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.localization.TranslationKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import finalforeach.cosmicreach.gamestates.PauseMenu;

@Mixin(value = PauseMenu.class, priority = 2000)
public class PauseMenuMixin {

	@Unique
	private static final TranslationKey TEXT_RESPAWN = new TranslationKey("pause_menu.respawn");
	@Unique
	private static final TranslationKey TEXT_OPTIONS = new TranslationKey("main_menu.options");
	@Unique
	private static final TranslationKey TEXT_SAVING = new TranslationKey("pause_menu.saving");

	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 0), index = 0)
	private String respawn(String text) {
		return TEXT_RESPAWN.getTranslated().string();
	}

	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 1), index = 0)
	private String returnToGame(String text) {
		return FluxConstants.TextReturnToGame.getTranslated().string();
	}

	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 2), index = 0)
	private String options(String text) {
		return TEXT_OPTIONS.getTranslated().string();
	}

	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 3), index = 0)
	private String returnToMainMenu(String text) {
		return FluxConstants.TextReturnToMain.getTranslated().string();
	}

	@ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0)
	private String saving(String x) {
		return TEXT_SAVING.getTranslated().string();
	}

}
