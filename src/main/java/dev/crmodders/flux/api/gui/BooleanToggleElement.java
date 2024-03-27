package dev.crmodders.flux.api.gui;

import finalforeach.cosmicreach.settings.BooleanSetting;
import org.pmw.tinylog.Logger;

public class BooleanToggleElement extends ToggleElement {

	private BooleanSetting setting;
	private String format, on, off;

	public BooleanToggleElement(float x, float y, float w, float h, BooleanSetting setting, String format, String on, String off) {
		super(x, y, w, h, setting.getValue(), false);
		this.setting = setting;
		this.format = format;
		this.on = on;
		this.off = off;
		onCreate();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		value = setting.getValue();
		super.updateText();
	}

	@Override
	public void onClick() {
		super.onClick();
		setting.setValue(value);
		super.updateText();
	}

	@Override
	public void updateText() {
		super.updateText();
		if (format != null) {
			super.setText(String.format(format, value ? on : off));
		}
	}

}