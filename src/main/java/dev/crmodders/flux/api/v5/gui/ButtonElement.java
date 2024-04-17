package dev.crmodders.flux.api.v5.gui;

import dev.crmodders.flux.api.v5.gui.base.BaseButton;

public class ButtonElement extends BaseButton {

	private final Runnable onClick;

	public ButtonElement(Runnable listener) {
		this.onClick = listener;
	}

	@Override
	public void onMouseReleased() {
		super.onMouseReleased();
		onClick.run();
	}
}
