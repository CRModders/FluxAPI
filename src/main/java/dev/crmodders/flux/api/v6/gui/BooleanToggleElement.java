package dev.crmodders.flux.api.v6.gui;

import finalforeach.cosmicreach.settings.BooleanSetting;

public class BooleanToggleElement extends ToggleElement {

	private final BooleanSetting setting;

	public BooleanToggleElement(BooleanSetting setting) {
		super(setting.getValue());
		this.setting = setting;
		updateText();
	}

	@Override
	public void onMouseReleased() {
		super.onMouseReleased();
		setting.setValue(value);
	}
}