package dev.crmodders.flux.ui.shapes;

import com.badlogic.gdx.graphics.Color;
import dev.crmodders.flux.ui.UIBatchBuilder;

import java.util.ArrayList;
import java.util.List;

public class ShapeBatchBuilder implements UIBatchBuilder {

	public List<Shape> shapes;
	public Color color;
	public float thickness;

	public ShapeBatchBuilder() {
		this.shapes = new ArrayList<>();
		this.color = Color.WHITE;
		this.thickness = 0.75f;
	}

	public void color(Color color) {
		this.color = color;
	}

	public void lineThickness(float thickness) {
		this.thickness = thickness;
	}

	public void drawRect(float x, float y, float w, float h) {
		shapes.add(new DrawRect(x, y, w, h, color, thickness));
	}

	public void fillRect(float x, float y, float w, float h) {
		shapes.add(new FillRect(x, y, w, h, color));
	}

	@Override
	public ShapeBatch build() {
		return new ShapeBatch(shapes);
	}

}
