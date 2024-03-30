package dev.crmodders.flux.api.renderer;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.Viewport;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.api.renderer.interfaces.Batch;
import dev.crmodders.flux.api.renderer.shapes.*;
import dev.crmodders.flux.api.renderer.text.StyleBatch;
import dev.crmodders.flux.api.renderer.text.TextBatch;
import dev.crmodders.flux.api.renderer.text.TextBatchBuilder;
import dev.crmodders.flux.api.renderer.text.TextLine;
import dev.crmodders.flux.font.Font;
import dev.crmodders.flux.util.text.StyleStringParser;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class UIRenderer {

	public static final String CHARACTER_SET;

	static {
		StringBuilder chars = new StringBuilder();

		List<UnicodeBlock> blocks = new ArrayList<>();
		blocks.add(UnicodeBlock.BASIC_LATIN);
//		blocks.add(UnicodeBlock.LATIN_1_SUPPLEMENT);
//		blocks.add(UnicodeBlock.LATIN_EXTENDED_A);
//		blocks.add(UnicodeBlock.LATIN_EXTENDED_ADDITIONAL);
//		blocks.add(UnicodeBlock.LATIN_EXTENDED_B);
//		blocks.add(UnicodeBlock.LATIN_EXTENDED_C);
//		blocks.add(UnicodeBlock.LATIN_EXTENDED_D);
//		blocks.add(UnicodeBlock.LATIN_EXTENDED_E);
//		blocks.add(UnicodeBlock.CYRILLIC);
//		blocks.add(UnicodeBlock.CYRILLIC_EXTENDED_A);
//		blocks.add(UnicodeBlock.CYRILLIC_EXTENDED_B);
//		blocks.add(UnicodeBlock.CYRILLIC_EXTENDED_C);
//		blocks.add(UnicodeBlock.CYRILLIC_SUPPLEMENTARY);
//		blocks.add(UnicodeBlock.KATAKANA);
//		blocks.add(UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS);
//		blocks.add(UnicodeBlock.CJK_COMPATIBILITY);
//		blocks.add(UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION);
//		blocks.add(UnicodeBlock.CJK_STROKES);
//		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
//		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
//		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B);
//		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C);
//		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D);
//		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_E);
//		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_F);
//		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_G);

		for (char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
			if (blocks.contains(UnicodeBlock.of(c))) {
				chars.append(c);
			}
		}

		CHARACTER_SET = chars.toString();
	}

	public static Texture white;
	public static FileHandle fontFile;
	public static Font font;
	public static UIRenderer uiRenderer;
	static {
		white = new Texture(FluxConstants.WhitePixel.load());
		fontFile = FluxConstants.FontFile.load();
		font = Font.generate(fontFile, 48, CHARACTER_SET);
		uiRenderer = new UIRenderer(GameState.batch);
	}

	public List<DrawBatch> batches;
	public SpriteBatch batch;
	public ShapeDrawer renderer;

	public UIRenderer(SpriteBatch batch) {
		this.batches = new ArrayList<>();
		this.batch = batch;
		this.renderer = new ShapeDrawer(batch, new TextureRegion(white));
	}

	public ShapeBatchBuilder buildShape() {
		return new ShapeBatchBuilder();
	}

	public TextBatchBuilder buildText() {
		return new TextBatchBuilder(font, 18.0f);
	}

	public TextBatchBuilder buildText(Font font, float fontSize) {
		return new TextBatchBuilder(font, fontSize);
	}

	public TextBatch createText(Font font, float fontSize, String string, Color color) {
		TextBatchBuilder builder = buildText(font, fontSize);
		builder.color(color);
		builder.append(string);
		return builder.build();
	}

	public TextBatch createStyledText(Font font, float fontSize, String string) {
		TextBatchBuilder builder = buildText(font, fontSize);
		StyleStringParser.parse(builder, string);
		return builder.build();
	}

	public void drawBatch(Batch batch, float x, float y) {
		batches.add(new DrawBatch(batch, x, y));
	}

	public void drawBatch(Batch batch, Viewport uiViewport, float xStart, float yStart, HorizontalAnchor hAnchor, VerticalAnchor vAnchor) {
		float w = batch.width();
		float h = batch.height();
		switch (hAnchor) {
		case LEFT_ALIGNED: {
			xStart -= uiViewport.getWorldWidth() / 2.0f;
			break;
		}
		case RIGHT_ALIGNED: {
			xStart = xStart + uiViewport.getWorldWidth() / 2.0f - w;
			break;
		}
		default: {
			xStart -= w / 2.0f;
		}
		}
		switch (vAnchor) {
		case TOP_ALIGNED: {
			yStart -= uiViewport.getWorldHeight() / 2.0f;
			break;
		}
		case BOTTOM_ALIGNED: {
			yStart = yStart + uiViewport.getWorldHeight() / 2.0f - h;
			break;
		}
		default: {
			yStart -= h / 2.0f;
		}
		}
		drawBatch(batch, xStart, yStart);
	}

	public void render(Matrix4 projectionMatrix) {
		batch.setProjectionMatrix(projectionMatrix);
		batch.begin();
		for (DrawBatch batch : batches) {
			if (batch.batch instanceof TextBatch textBatch) {
				render(textBatch, batch.x, batch.y);
			} else if (batch.batch instanceof ShapeBatch shapeBatch) {
				render(shapeBatch, batch.x, batch.y);
			}
		}
		batches.clear();
		batch.end();
	}

	public void render(TextBatch textBatch, float xStart, float yStart) {
		Matrix4 matrix = new Matrix4();
		for (TextLine line : textBatch.lines()) {
			matrix.idt();
			matrix.translate(xStart, yStart, 0);

			for (StyleBatch batch : line.batches) {
				this.batch.setColor(1,1,1, batch.alpha);
				this.batch.setTransformMatrix(matrix);

				for(int page = 0; page < batch.pageVertices.length; page++) {
					TextureRegion region = batch.font.bitmapFont.getRegion(page);
					float[] vertices = batch.pageVertices[page];
					this.batch.draw(region.getTexture(), vertices, 0, vertices.length);
				}

				if(batch.underline) {
					this.renderer.line(0, batch.fontSize, batch.width, batch.fontSize);
				}

				if(batch.strikethrough) {
					this.renderer.line(0, batch.fontSize * 1/2f, batch.width, batch.fontSize * 1/2f, 2f);
				}

				matrix.translate(batch.width, 0, 0);
			}

			yStart += line.height();
		}
		matrix.idt();
		this.batch.setTransformMatrix(matrix);
	}

	public void render(ShapeBatch shapeBatch, float xStart, float yStart) {
		for (Shape shape : shapeBatch.shapes) {

			if (shape instanceof DrawRect rect) {
				renderer.rectangle(xStart + rect.x, yStart + rect.y, rect.w, rect.h, rect.color, rect.thickness);
			} else if (shape instanceof FillRect rect) {
				float x1 = xStart + rect.x + rect.w;
				float y1 = yStart + rect.y;
				float x2 = xStart + rect.x;
				float y2 = yStart + rect.y + rect.h;
				renderer.filledTriangle(x1, y1, x2, y1, x2, y2, rect.color);
				renderer.filledTriangle(x2, y2, x1, y2, x1, y1, rect.color);
			}
		}
	}

	public static class DrawBatch {
		public Batch batch;
		public float x, y;

		public DrawBatch(Batch batch, float x, float y) {
			this.batch = batch;
			this.x = x;
			this.y = y;
		}

	}

}
