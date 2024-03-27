package dev.crmodders.flux.api.gui.interfaces;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

public interface GameStateInterface {
	Viewport getViewport();

	OrthographicCamera getCamera();

}
