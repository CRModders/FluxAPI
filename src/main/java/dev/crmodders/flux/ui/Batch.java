package dev.crmodders.flux.ui;

import com.badlogic.gdx.utils.viewport.Viewport;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;

public interface Batch {

	void render(UIRenderer uiRenderer, float xStart, float yStart);

	default void render(UIRenderer uiRenderer, Viewport uiViewport, float xStart, float yStart, HorizontalAnchor hAnchor, VerticalAnchor vAnchor) {
		float w = width();
		float h = height();
		switch (hAnchor) {
			case LEFT_ALIGNED: {
				xStart -= uiViewport.getWorldWidth() / 2.0f;
				break;
			}
			case RIGHT_ALIGNED: {
				xStart = xStart + uiViewport.getWorldWidth() / 2.0f - w;
				break;
			}
			default: {
				xStart -= w / 2.0f;
			}
		}
		switch (vAnchor) {
			case TOP_ALIGNED: {
				yStart -= uiViewport.getWorldHeight() / 2.0f;
				break;
			}
			case BOTTOM_ALIGNED: {
				yStart = yStart + uiViewport.getWorldHeight() / 2.0f - h;
				break;
			}
			default: {
				yStart -= h / 2.0f;
			}
		}
		render(uiRenderer, xStart, yStart);
	}

	float width();

	float height();
}
