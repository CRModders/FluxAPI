package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;
import finalforeach.cosmicreach.settings.ControlSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.badlogic.gdx.math.MathUtils;

import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.LoadingGame;

import java.util.List;

@Mixin(value = LoadingGame.class, priority = 2000)
public class LoadingGameMixin extends GameState {

	@Shadow
	private int tipIndex;
	@Shadow
	private String[] tips;
	@Shadow
	private boolean isOdd;

	private static final TranslationKey TEXT_TIPS = new TranslationKey("loading_menu.tips");

	private static final TranslationKey TEXT_ODD_TIPS = new TranslationKey("loading_menu.odd_tips");

	private static final TranslationKey TEXT_LOADING = new TranslationKey("loading_menu.loading");

	@Inject(method = "create", at = @At("TAIL"))
	private void tips(CallbackInfo ci) {
		List<TranslationString> tips = TEXT_TIPS.getTranslatedList();
		String[] normal = new String[tips.size()];
		for (int i = 0; i < normal.length; i++) {
			normal[i] = tips.get(i).format(ControlSettings.keyHideUI.getKeyName(), ControlSettings.keyScreenshot.getKeyName(), ControlSettings.keyDebugInfo.getKeyName(),
					ControlSettings.keyDropItem.getKeyName(), ControlSettings.keyInventory.getKeyName());
		}

		List<TranslationString> odd_tips = TEXT_ODD_TIPS.getTranslatedList();
		String[] odd = new String[odd_tips.size()];
		for (int i = 0; i < odd.length; i++) {
			odd[i] = odd_tips.get(i).string();
		}

		this.tips = isOdd ? odd : normal;
		this.tipIndex = MathUtils.random(0, this.tips.length - 1);

	}

	@ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0)
	private String loading(String x) {
		if (x.equals("Loading")) {
			return TEXT_LOADING.getTranslated().string();
		}
		return x;
	}

}
