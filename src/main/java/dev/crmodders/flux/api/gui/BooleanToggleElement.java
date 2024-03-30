package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.settings.BooleanSetting;

public class BooleanToggleElement extends ToggleElement {

	private BooleanSetting setting;

	public BooleanToggleElement(BooleanSetting setting, TranslationKey format) {
		this(0, 0,0,0,setting,format);
	}
	public BooleanToggleElement(float x, float y, float w, float h, BooleanSetting setting, TranslationKey textKey) {
		super(x, y, w, h, setting.getValue(), false, textKey);
		this.setting = setting;
		onCreate();
		updateText();
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

}