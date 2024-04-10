package dev.crmodders.flux.ui.shapes;

import com.badlogic.gdx.graphics.Color;

public class FillRect implements Shape {

	public float x, y, w, h;
	public Color color;

	public FillRect(float x, float y, float w, float h, Color color) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
	}

}
