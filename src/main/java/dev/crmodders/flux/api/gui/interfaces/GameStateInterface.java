package dev.crmodders.flux.api.gui.interfaces;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.renderer.interfaces.Component;

import java.util.List;

public interface GameStateInterface {
	Viewport getViewport();

	OrthographicCamera getCamera();

	List<Component> getComponents();

}
