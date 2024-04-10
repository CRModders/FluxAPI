package dev.crmodders.flux.menus;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.BooleanToggleElement;
import dev.crmodders.flux.api.gui.SteppedIntSliderElement;
import dev.crmodders.flux.api.gui.SwitchGameStateButtonElement;
import dev.crmodders.flux.api.gui.interfaces.GameStateCache;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.ui.UIRenderer;
import finalforeach.cosmicreach.gamestates.GameState;

public class FluxOptionMenu extends LayoutMenu {

	private static final int[] MSAA_STEPS = {0, 2, 4, 8, 16};

	public FluxOptionMenu(GameState previousState) {
		super(previousState);

		SteppedIntSliderElement msaa = new SteppedIntSliderElement(MSAA_STEPS, FluxSettings.AntiAliasing);
		msaa.translation = new TranslationKey("fluxapi:flux_options.msaa");
		msaa.updateText();
		addFluxElement(msaa);

		BooleanToggleElement font = new BooleanToggleElement(FluxSettings.UseAlternativeFont) {
			@Override
			public void onMouseReleased() {
				super.onMouseReleased();
				if(FluxSettings.UseAlternativeFont.getValue()) {
					UIRenderer.font = UIRenderer.comicSansFont;
				} else {
					UIRenderer.font = UIRenderer.cosmicReachFont;
				}
				GameStateCache.getCache().invalidateCachedAssets();
			}
		};
		font.translation = new TranslationKey("fluxapi:flux_options.use_alternative_font");
		font.updateText();
		addFluxElement(font);


		SwitchGameStateButtonElement language = new SwitchGameStateButtonElement(() -> new LanguagePickerMenu(GameState.currentGameState));
		language.translation = new TranslationKey("fluxapi:flux_options.languages");
		language.updateText();
		addFluxElement(language);

		setLayoutEnabled(false);
		addDoneButton();
	}

}
