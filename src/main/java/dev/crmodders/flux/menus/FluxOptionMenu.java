package dev.crmodders.flux.menus;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.BooleanToggleElement;
import dev.crmodders.flux.api.gui.ButtonElement;
import dev.crmodders.flux.api.gui.LanguageSelectorElement;
import dev.crmodders.flux.api.gui.SteppedIntSliderElement;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;

import java.util.Locale;

public class FluxOptionMenu extends MenuState {

	public static final TranslationKey KEY_VECTOR_FONT_RENDERER = new TranslationKey("fluxapi:options.flux_options.vector_font_renderer");
	public static final TranslationKey KEY_LANGUAGE = new TranslationKey("fluxapi:options.flux_options.language");

	private int ix = 0;
	private int iy = 0;

	public FluxOptionMenu(GameState previousState) {
		super(previousState);
		ButtonElement doneButton = new ButtonElement(0.0f, -50.0f, 250.0f, 50.0f, b -> GameState.switchToGameState(previousState));
		doneButton.vAnchor = VerticalAnchor.BOTTOM_ALIGNED;
		doneButton.setText("Done");
		doneButton.show();

		SteppedIntSliderElement msaa = new SteppedIntSliderElement(0, 0, 0, 0, 0, 16, new int[] { 0, 2, 4, 8, 16 }, FluxSettings.AntiAliasing, "MSAA: %dx");
		addElement(msaa);

		BooleanToggleElement font = new BooleanToggleElement(0, 0, 0, 0, FluxSettings.ReplaceFontRenderer, "Vector Font Renderer %s", "On", "Off");
		addElement(font);

		LanguageSelectorElement locale = new LanguageSelectorElement(0,0,0,0, FluxSettings.LanguageSetting, TranslationApi.getLanguages()) {
			@Override
			public void updateLocale(Locale locale) {
				super.updateLocale(locale);
				TranslationApi.setLanguage(locale);
			}

			@Override
			public void updateText() {
				Locale selected = getSelected();
				TranslationString string = FluxSettings.SelectedLanguage.getTranslatedString(KEY_LANGUAGE);
				setText(string.format(selected.getDisplayName()));
			}
		};
		addElement(locale);

		this.uiElements.add(doneButton);
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
