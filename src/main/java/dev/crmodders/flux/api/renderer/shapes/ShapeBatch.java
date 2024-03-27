package dev.crmodders.flux.api.renderer.shapes;

import java.util.List;

import dev.crmodders.flux.api.renderer.interfaces.Batch;

public class ShapeBatch implements Batch {

	public List<Shape> shapes;

	public ShapeBatch(List<Shape> shapes) {
		this.shapes = shapes;
	}

	@Override
	public float width() {
		return 0;
	}

	@Override
	public float height() {
		return 0;
	}

}
