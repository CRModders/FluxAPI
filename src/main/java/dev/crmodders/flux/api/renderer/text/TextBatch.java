package dev.crmodders.flux.api.renderer.text;

import dev.crmodders.flux.api.renderer.interfaces.Batch;

import java.util.List;

public class TextBatch implements Batch {

	private List<TextLine> lines;
	private float width;
	private float height;

	public TextBatch(List<TextLine> batches) {
		this.lines = batches;
		this.width = 0f;
		this.height = 0f;
		for (TextLine line : lines) {
			width = Math.max(width, line.width());
			height += line.height();
		}
	}

	public TextBatch(List<TextLine> batches, float width, float height) {
		this.lines = batches;
		this.width = width;
		this.height = height;
	}

	public List<TextLine> lines() {
		return lines;
	}

	@Override
	public float width() {
		return width;
	}

	@Override
	public float height() {
		return height;
	}

}
