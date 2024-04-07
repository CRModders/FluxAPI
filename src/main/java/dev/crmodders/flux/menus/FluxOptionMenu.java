package dev.crmodders.flux.menus;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.*;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;

import java.util.Locale;

public class FluxOptionMenu extends LayoutMenu {

	public static final TranslationKey TEXT_MSAA = new TranslationKey("fluxapi:flux_options.msaa");
	public static final TranslationKey TEXT_LANGUAGES = new TranslationKey("fluxapi:flux_options.languages");

	private static final int[] MSAA_STEPS = {0, 2, 4, 8, 16};

	public FluxOptionMenu(GameState previousState) {
		super(previousState);

		SteppedIntSliderElement msaa = new SteppedIntSliderElement(MSAA_STEPS, FluxSettings.AntiAliasing);
		msaa.translation = TEXT_MSAA;
		msaa.updateText();
		addFluxElement(msaa);

		SwitchGameStateButtonElement language = new SwitchGameStateButtonElement(() -> new LanguagePickerMenu(GameState.currentGameState));
		language.translation = TEXT_LANGUAGES;
		language.updateText();
		addFluxElement(language);

		setLayoutEnabled(false);
		addDoneButton();
	}

}
