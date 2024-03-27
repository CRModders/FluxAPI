package dev.crmodders.flux.api.gui;

import finalforeach.cosmicreach.settings.FloatSetting;
import finalforeach.cosmicreach.ui.UISlider;

public class FloatSliderElement extends UISlider {

	private String format;
	private FloatSetting setting;

	public FloatSliderElement(float x, float y, float w, float h, float min, float max, FloatSetting setting, String format) {
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
		setting.setValue(currentValue);
		this.updateText();
	}

	@Override
	public void validate() {
		super.validate();
		this.updateText();
	}

	@Override
	public void updateText() {
		super.updateText();
		if(format!=null) {
			super.setText(String.format(format, currentValue));
		}
	}

}
