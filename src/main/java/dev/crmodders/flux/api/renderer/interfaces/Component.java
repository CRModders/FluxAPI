package dev.crmodders.flux.api.renderer.interfaces;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.renderer.UIRenderer;

public interface Component {

	void update(UIRenderer uiRenderer, Viewport viewport, Vector2 mouse);

	void paint(UIRenderer renderer);

	void draw(UIRenderer renderer, Viewport viewport);

	boolean isDirty();

}
