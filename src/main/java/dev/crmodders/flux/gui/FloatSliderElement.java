package dev.crmodders.flux.gui;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.gui.base.BaseSlider;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.settings.FloatSetting;

public class FloatSliderElement extends BaseSlider {

	private final FloatSetting setting;

	public FloatSliderElement(float min, float max, FloatSetting setting) {
		super(min, max);
		this.setting = setting;
		this.value = setting.getValue();
		updateText();
	}

	@Override
	public void onMouseReleased() {
		super.onMouseReleased();
		setting.setValue(value);
	}

	@Override
	public String updateTranslation(TranslationKey key) {
		if(key == null) {
			return String.valueOf(value);
		} else {
			return FluxSettings.SelectedLanguage.get(key).format(String.valueOf(value));
		}
	}
}
