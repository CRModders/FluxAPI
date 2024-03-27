package dev.crmodders.flux.api.gui;

import finalforeach.cosmicreach.settings.IntSetting;
import finalforeach.cosmicreach.ui.UISlider;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class SteppedIntSliderElement extends UISlider {

	private String format;
	private IntSetting setting;
	private int[] values;

	public SteppedIntSliderElement(float x, float y, float w, float h, int min, int max, int[] values, IntSetting setting, String format) {
		super(min, max, setting.getValue(), x, y, w, h);
		this.format = format;
		this.setting = setting;
		this.values = values;
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
		int n = (int) this.currentValue;
		int c = Arrays.stream(values).boxed().min(Comparator.comparingInt(i -> Math.abs(i - n))).orElseThrow(() -> new NoSuchElementException("No value present"));
		this.currentValue = c;
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
