package dev.crmodders.flux.api.renderer.text;

import java.util.ArrayList;
import java.util.List;

public class TextLine {

	public List<StyleBatch> batches;

	private float width;
	private float height;

	public TextLine() {
		this.batches = new ArrayList<>();
		this.width = 0f;
		this.height = 0f;
	}

	public List<StyleBatch> getBatches() {
		return batches;
	}

	public void append(StyleBatch batch) {
		batches.add(batch);
		for (int i = 0; i < batch.chars.length(); i++) {
			char chr = batch.chars.charAt(i);
			width += batch.font.glyph(chr).advance * batch.fontSize;
			height = Math.max(height, batch.fontSize);
		}
	}

	public float width() {
		return width;
	}

	public float height() {
		return height;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(StyleBatch batch : batches) {
			sb.append(batch.chars);
		}
		return sb.toString();
	}
}
