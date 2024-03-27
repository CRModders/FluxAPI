package dev.crmodders.flux.api.gui;

import finalforeach.cosmicreach.settings.IntSetting;
import finalforeach.cosmicreach.ui.UISlider;

public class IntSliderElement extends UISlider {

	private String format;
	private IntSetting setting;

	public IntSliderElement(float x, float y, float w, float h, int min, int max, IntSetting setting, String format) {
		super(min, max, setting.getValue(), x, y, w, h);
		this.format = format;
		this.setting = setting;
		updateText();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.updateText();
	}

	@Override
	public void onMouseDown() {
		super.onMouseDown();
		this.currentValue = setting.getValue();
		this.updateText();
	}

	@Override
	public void onMouseUp() {
		super.onMouseUp();
		setting.setValue((int) currentValue);
		this.updateText();
	}

	@Override
	public void validate() {
		super.validate();
		super.currentValue = (int) super.currentValue;
		this.updateText();
	}

	@Override
	public void updateText() {
		super.updateText();
		if (format != null) {
			super.setText(String.format(format, (int) currentValue));
		}
	}

}
