package dev.crmodders.flux.api.renderer.shapes;

import dev.crmodders.flux.api.renderer.interfaces.Batch;

import java.util.List;

public class ShapeBatch implements Batch {

	public List<Shape> shapes;

	public ShapeBatch(List<Shape> shapes) {
		this.shapes = shapes;
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
