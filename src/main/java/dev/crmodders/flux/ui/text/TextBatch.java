package dev.crmodders.flux.ui.text;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import dev.crmodders.flux.ui.Batch;
import dev.crmodders.flux.ui.UIRenderer;

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
			for(StyleBatch batch : line.batches) {
				batch.calculateVertices();
			}
			width = Math.max(width, line.width());
			height += line.height();
		}
	}

	public TextBatch(List<TextLine> batches, float width, float height) {
		this.lines = batches;
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(UIRenderer uiRenderer, float xStart, float yStart) {
		Matrix4 matrix = new Matrix4();
		for (TextLine line : lines()) {
			matrix.idt();
			matrix.translate(xStart, yStart, 0);

			for (StyleBatch batch : line.batches) {
				uiRenderer.batch.setColor(1,1,1, batch.alpha);
				uiRenderer.batch.setTransformMatrix(matrix);

				for(int page = 0; page < batch.pageVertices.length; page++) {
					TextureRegion region = batch.font.bitmapFont.getRegion(page);
					float[] vertices = batch.pageVertices[page];
					uiRenderer.batch.draw(region.getTexture(), vertices, 0, vertices.length);
				}
				if(batch.underline) {
					uiRenderer.renderer.line(0, batch.fontSize, batch.width, batch.fontSize);
				}

				if(batch.strikethrough) {
					uiRenderer.renderer.line(0, batch.fontSize * 1/2f, batch.width, batch.fontSize * 1/2f, 2f);
				}
				matrix.translate(batch.width, 0, 0);
			}

			yStart += line.height();
		}
		matrix.idt();
		uiRenderer.batch.setTransformMatrix(matrix);
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(TextLine line : lines) {
			sb.append(line.toString()).append('\n');
		}
		return sb.toString();
	}
}
