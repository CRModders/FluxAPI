package dev.crmodders.flux.api.gui.interfaces;

import com.badlogic.gdx.utils.viewport.Viewport;

public interface UIElementInterface {

	float displayX(Viewport uiViewport);

	float displayY(Viewport uiViewport);

	void update(Viewport uiViewport, float mouseX, float mouseY);

}
