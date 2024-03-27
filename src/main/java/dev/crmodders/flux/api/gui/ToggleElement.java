package dev.crmodders.flux.api.gui;

import com.badlogic.gdx.Gdx;
import finalforeach.cosmicreach.settings.GraphicsSettings;
import finalforeach.cosmicreach.ui.UIElement;

public class ToggleElement extends UIElement {

	protected boolean value;

	public ToggleElement(float x, float y, float w, float h, boolean defaultValue) {
		super(x, y, w, h);
		this.value = defaultValue;
	}

	public ToggleElement(float x, float y, float w, float h, boolean defaultValue, boolean triggerOnCreate) {
		super(x, y, w, h, triggerOnCreate);
		this.value = defaultValue;
	}

	@Override
	public void onClick() {
		super.onClick();
		value = !value;
		this.updateText();
	}

	@Override
	public void setText(String text) {
		super.setText(text);
	}
}
