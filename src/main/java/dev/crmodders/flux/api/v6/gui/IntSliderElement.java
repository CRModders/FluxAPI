package dev.crmodders.flux.api.v6.gui;

import dev.crmodders.flux.api.v6.gui.base.BaseSlider;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.settings.IntSetting;

public class IntSliderElement extends BaseSlider {

	private final IntSetting setting;

	public IntSliderElement(int min, int max, IntSetting setting) {
		super(min, max);
		this.setting = setting;
		this.value = setting.getValue();
		updateText();
	}

	@Override
	public void onMouseReleased() {
		super.onMouseReleased();
		setting.setValue((int)value);
	}

	@Override
	public String updateTranslation(TranslationKey key) {
		if(key == null) {
			return String.valueOf((int)value);
		} else {
			return key.getTranslated().format(String.valueOf((int)value));
		}
	}

	@Override
	public float validate(float value) {
		return (int) super.validate(value);
	}
}
