package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.api.gui.base.BaseButton;
import dev.crmodders.flux.api.settings.LocaleSetting;
import dev.crmodders.flux.localization.TranslationKey;

import java.util.List;
import java.util.Locale;

public class LanguageSelectorElement extends BaseButton {

	private final LocaleSetting setting;
	private final List<Locale> languages;
	private int selected;

	public LanguageSelectorElement(LocaleSetting setting, List<Locale> languages) {
		this.setting = setting;
		this.languages = languages;
		this.selected = languages.indexOf(setting.getValue());
	}

	@Override
	public void onMouseReleased() {
		super.onMouseReleased();
		selected++;
		selected %= languages.size();
		setting.setValue(languages.get(selected));
		updateLocale(languages.get(selected));
		updateText();
	}

	@Override
	public String updateTranslation(TranslationKey key) {
		Locale selected = getSelected();
		if(key == null) {
			return selected.getDisplayName();
		} else {
			return key.getTranslated().format(selected.getDisplayName());
		}
	}

	public void updateLocale(Locale locale) {
	}
	public Locale getSelected() {
		return this.languages.get(this.selected);
	}

	public void setSelected(Locale locale) {
		this.selected = languages.indexOf(locale);
	}

}
