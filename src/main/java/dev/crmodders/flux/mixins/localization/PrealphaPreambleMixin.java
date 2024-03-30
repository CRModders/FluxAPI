package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.localization.TranslationKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.PrealphaPreamble;

@Mixin(value = PrealphaPreamble.class, priority = 2000)
abstract public class PrealphaPreambleMixin extends GameState {

	@Unique
	private static final TranslationKey TEXT_PREAMBLE = new TranslationKey("pre_alpha_preamble.pre_alpha_text");

	@ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0)
	private String prealphaWarningText(String x) {
		return TEXT_PREAMBLE.getTranslated().string();
	}

}
