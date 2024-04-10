package dev.crmodders.flux.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public interface Component {

	void update(UIRenderer uiRenderer, Viewport viewport, Vector2 mouse);

	void paint(UIRenderer renderer);

	void draw(UIRenderer renderer, Viewport viewport);

	void deactivate();

	boolean isDirty();

}
