package dev.crmodders.flux.menus;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.LanguageSelectorElement;
import dev.crmodders.flux.api.gui.SteppedIntSliderElement;
import dev.crmodders.flux.api.gui.SwitchGameStateButtonElement;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;

import java.util.Locale;

public class FluxOptionMenu extends LayoutMenu {

	public static final TranslationKey TEXT_MSAA = new TranslationKey("fluxapi:flux_options.msaa");
	public static final TranslationKey TEXT_LANGUAGE = new TranslationKey("fluxapi:flux_options.language");
	private static final int[] MSAA_STEPS = {0, 2, 4, 8, 16};

	public FluxOptionMenu(GameState previousState) {
		super(previousState);

		SteppedIntSliderElement msaa = new SteppedIntSliderElement(0, 16, MSAA_STEPS, FluxSettings.AntiAliasing, TEXT_MSAA);
		addUIElement(msaa);

		LanguageSelectorElement locale = new LanguageSelectorElement(FluxSettings.LanguageSetting, TranslationApi.getLanguages(), TEXT_LANGUAGE) {
			@Override
			public void updateLocale(Locale locale) {
				super.updateLocale(locale);
				TranslationApi.setLanguage(locale);
			}
		};
		addUIElement(locale);

		setLayoutEnabled(false);
		addDoneButton();
	}

}
