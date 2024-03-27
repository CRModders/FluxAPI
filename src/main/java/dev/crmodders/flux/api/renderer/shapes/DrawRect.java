package dev.crmodders.flux.api.renderer.shapes;

import com.badlogic.gdx.graphics.Color;

public class DrawRect implements Shape {
	public float x, y, w, h;
	public Color color;
	public float thickness;

	public DrawRect(float x, float y, float w, float h, Color color, float thickness) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
		this.thickness = thickness;
	}

}
