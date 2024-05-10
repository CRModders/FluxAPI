package dev.crmodders.flux.ui.shapes;

import dev.crmodders.flux.ui.Batch;
import dev.crmodders.flux.ui.UIRenderer;

import java.util.List;

public class ShapeBatch implements Batch {

	public List<Shape> shapes;

	public ShapeBatch(List<Shape> shapes) {
		this.shapes = shapes;
	}

	@Override
	public void render(UIRenderer uiRenderer, float xStart, float yStart) {
		for (Shape shape : shapes) {
			if (shape instanceof DrawRect rect) {
				uiRenderer.renderer.rectangle(xStart + rect.x, yStart + rect.y, rect.w, rect.h, rect.color, rect.thickness);
			} else if (shape instanceof FillRect rect) {
				float x1 = xStart + rect.x + rect.w;
				float y1 = yStart + rect.y;
				float x2 = xStart + rect.x;
				float y2 = yStart + rect.y + rect.h;
				uiRenderer.renderer.filledTriangle(x1, y1, x2, y1, x2, y2, rect.color);
				uiRenderer.renderer.filledTriangle(x2, y2, x1, y2, x1, y1, rect.color);
			}
		}
	}

	@Override
	public float width() {
		return 0;
	} // TODO

	@Override
	public float height() {
		return 0;
	} // TODO

}
