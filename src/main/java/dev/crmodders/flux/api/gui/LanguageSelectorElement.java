package dev.crmodders.flux.api.gui;

import java.util.List;
import java.util.Locale;

import dev.crmodders.flux.api.settings.LocaleSetting;
import finalforeach.cosmicreach.ui.UIElement;

public class LanguageSelectorElement extends UIElement {

	private final LocaleSetting setting;
	private final List<Locale> languages;
	private int selected;

	public LanguageSelectorElement(float x, float y, float w, float h, LocaleSetting setting, List<Locale> languages) {
		super(x, y, w, h);
		this.languages = languages;
		this.selected = this.languages.indexOf(setting.getValue());
		this.setting = setting;
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
		setText(selected.getDisplayName());
	}

}
