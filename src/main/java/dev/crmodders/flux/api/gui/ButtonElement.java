package dev.crmodders.flux.api.gui;

import finalforeach.cosmicreach.ui.UIElement;

public class ButtonElement extends UIElement {
	public static interface ButtonListener {
		void click(ButtonElement button);
	}

	private ButtonListener listener;

	public ButtonElement(float x, float y, float w, float h, ButtonListener listener) {
		super(x, y, w, h);
		this.listener = listener;
	}

	@Override
	public void onClick() {
		super.onClick();
		listener.click(this);
	}

}
