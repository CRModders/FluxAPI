package dev.crmodders.flux.api.v6.gui.interfaces;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.ui.Component;

import java.util.List;

/**
 * A basic gameState structure for all states to add some getters
 */
public interface GameStateInterface {
	Viewport getViewport();

	OrthographicCamera getCamera();

	List<Component> getComponents();

}
