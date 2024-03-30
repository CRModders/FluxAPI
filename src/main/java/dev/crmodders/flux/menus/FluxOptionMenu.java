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

public class FluxOptionMenu extends MenuState {

	public static final TranslationKey TEXT_MSAA = new TranslationKey("fluxapi:flux_options.msaa");
	public static final TranslationKey TEXT_LANGUAGE = new TranslationKey("fluxapi:flux_options.language");

	private static final int[] MSAA_STEPS = {0, 2, 4, 8, 16};

	private int ix = 0;
	private int iy = 0;

	public FluxOptionMenu(GameState previousState) {
		super(previousState);

		SteppedIntSliderElement msaa = new SteppedIntSliderElement(0, 16, MSAA_STEPS, FluxSettings.AntiAliasing, TEXT_MSAA);
		addElement(msaa);

		LanguageSelectorElement locale = new LanguageSelectorElement(FluxSettings.LanguageSetting, TranslationApi.getLanguages(), TEXT_LANGUAGE) {
			@Override
			public void updateLocale(Locale locale) {
				super.updateLocale(locale);
				TranslationApi.setLanguage(locale);
			}
		};
		addElement(locale);

		SwitchGameStateButtonElement doneButton = new SwitchGameStateButtonElement(0.0f, -50.0f, 250.0f, 50.0f, () -> previousState, FluxConstants.TextDone);
		doneButton.vAnchor = VerticalAnchor.BOTTOM_ALIGNED;
		doneButton.show();
		uiElements.add(doneButton);
	}

	public void addElement(UIElement element) {
		element.x = 275.0f * ((float) this.iy - 1.0f);
		element.y = 50 + 75 * this.ix;
		element.w = 250;
		element.h = 50;
		element.vAnchor = VerticalAnchor.TOP_ALIGNED;
		element.updateText();
		element.show();
		uiElements.add(element);
		this.ix++;
		if (this.ix > 5) {
			this.ix = 0;
			this.iy++;
		}
	}

}
