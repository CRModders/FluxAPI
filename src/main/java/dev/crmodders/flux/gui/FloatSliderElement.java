package dev.crmodders.flux.gui;

import dev.crmodders.flux.gui.base.BaseSlider;
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

}
