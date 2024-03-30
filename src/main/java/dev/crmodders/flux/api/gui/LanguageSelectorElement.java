package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.api.settings.LocaleSetting;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;
import finalforeach.cosmicreach.ui.UIElement;

import java.util.List;
import java.util.Locale;

public class LANGUAGESelectorElement extends UIElement {

	private final LocaleSetting setting;
	private final List<Locale> LANGUAGES;
	private int selected;

	public LANGUAGESelectorElement(LocaleSetting setting, List<Locale> LANGUAGES, TranslationKey textKey) {
		this(0, 0, 0, 0, setting, LANGUAGES, textKey);
	}

	public LANGUAGESelectorElement(float x, float y, float w, float h, LocaleSetting setting, List<Locale> LANGUAGES, TranslationKey textKey) {
		super(x, y, w, h, false);
		((UIElementInterface) this).setTextKey(textKey);
		this.LANGUAGES = LANGUAGES;
		this.selected = this.LANGUAGES.indexOf(setting.getValue());
		this.setting = setting;
		onCreate();
		updateText();
	}

	@Override
	public void onClick() {
		super.onClick();
		selected++;
		selected %= LANGUAGES.size();
		setting.setValue(LANGUAGES.get(selected));
		updateLocale(LANGUAGES.get(selected));
		updateText();
	}

	public Locale getSelected() {
		return this.LANGUAGES.get(this.selected);
	}

	public void updateLocale(Locale locale) {
	}

	@Override
	public void updateText() {
		super.updateText();
		Locale selected = getSelected();
		TranslationKey textKey = ((UIElementInterface) this).getTextKey();
		if (textKey != null) {
			TranslationString text = FluxSettings.SelectedLanguage.getTranslatedString(textKey);
			setText(text.format(selected.getDisplayName()));
		}
	}

}
