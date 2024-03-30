package dev.crmodders.flux.api.gui;

import java.util.List;
import java.util.Locale;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.api.settings.LocaleSetting;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;
import finalforeach.cosmicreach.ui.UIElement;

public class LanguageSelectorElement extends UIElement {

	private final LocaleSetting setting;
	private final List<Locale> languages;
	private int selected;

	public LanguageSelectorElement(LocaleSetting setting, List<Locale> languages, TranslationKey textKey) {
		this(0, 0, 0, 0, setting, languages, textKey);
	}

	public LanguageSelectorElement(float x, float y, float w, float h, LocaleSetting setting, List<Locale> languages, TranslationKey textKey) {
		super(x, y, w, h, false);
		((UIElementInterface) this).setTextKey(textKey);
		this.languages = languages;
		this.selected = this.languages.indexOf(setting.getValue());
		this.setting = setting;
		onCreate();
	}

	@Override
	public void onClick() {
		super.onClick();
		selected++;
		selected %= languages.size();
		setting.setValue(languages.get(selected));
		updateLocale(languages.get(selected));
		updateText();
	}

	public Locale getSelected() {
		return this.languages.get(this.selected);
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
