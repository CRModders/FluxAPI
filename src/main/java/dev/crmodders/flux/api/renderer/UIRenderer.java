package dev.crmodders.flux.api.renderer;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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
import dev.crmodders.flux.font.VectorFont;
import dev.crmodders.flux.font.VectorGlyph;
import dev.crmodders.flux.util.text.StyleStringParser;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import org.pmw.tinylog.Logger;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class UIRenderer {

	public static Texture white = new Texture(GameAssetLoader.loadAsset("fluxapi:whitepixel.png"));
	public static UIRenderer uiRenderer = new UIRenderer(GameState.batch);
	public static final String CHARACTER_SET;

	static {
		StringBuilder chars = new StringBuilder();

		List<UnicodeBlock> blocks = new ArrayList<>();
		blocks.add(UnicodeBlock.BASIC_LATIN);
		blocks.add(UnicodeBlock.LATIN_1_SUPPLEMENT);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_A);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_ADDITIONAL);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_B);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_C);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_D);
		blocks.add(UnicodeBlock.LATIN_EXTENDED_E);
		blocks.add(UnicodeBlock.CYRILLIC);
		blocks.add(UnicodeBlock.CYRILLIC_EXTENDED_A);
		blocks.add(UnicodeBlock.CYRILLIC_EXTENDED_B);
		blocks.add(UnicodeBlock.CYRILLIC_EXTENDED_C);
		blocks.add(UnicodeBlock.CYRILLIC_SUPPLEMENTARY);
		blocks.add(UnicodeBlock.KATAKANA);
		blocks.add(UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS);
		blocks.add(UnicodeBlock.CJK_COMPATIBILITY);
		blocks.add(UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION);
		blocks.add(UnicodeBlock.CJK_STROKES);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_E);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_F);
		blocks.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_G);

		for (char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
			if (blocks.contains(UnicodeBlock.of(c))) {
				chars.append(c);
			}
		}

		CHARACTER_SET = chars.toString();
	}

	public static FileHandle fontFile = GameAssetLoader.loadAsset(FluxConstants.FontFile.toString());
	public static VectorFont font;
	static {
		Font awtFont = new Font("Arial", 0, 1);
		try (InputStream is = fontFile.read()) {
			awtFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		font = new VectorFont(awtFont, CHARACTER_SET);
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

	public TextBatchBuilder buildText(VectorFont font, float fontSize) {
		return new TextBatchBuilder(font, fontSize);
	}

	public TextBatch createText(VectorFont font, float fontSize, String string, Color color) {
		TextBatchBuilder builder = buildText(font, fontSize);
		builder.color(color);
		builder.append(string);
		return builder.build();
	}

	public TextBatch createStyledText(VectorFont font, float fontSize, String string) {
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
		float y = yStart;
		for (TextLine line : textBatch.lines()) {

			float x = xStart;
			for (StyleBatch batch : line.batches) {
				renderer.setColor(batch.color.r, batch.color.g, batch.color.b, batch.color.a * batch.alpha);

				float fontSizeX = batch.bold ? batch.fontSize * 1.25f : batch.fontSize;
				float fontSizeY = batch.fontSize;

				for (int i = 0; i < batch.chars.length(); i++) {
					char chr = batch.chars.charAt(i);
					VectorGlyph glyph = batch.font.glyph(chr);
					float glyphAscent = glyph.ascent * fontSizeY;
					float glyphAdvance = glyph.advance * fontSizeX;

					for (int j = 0; j < glyph.geom.length / 3; j++) {
						float x1 = glyph.geom[j * 3 + 0].x * fontSizeX + x;
						float y1 = glyph.geom[j * 3 + 0].y * fontSizeY + glyphAscent + y;

						float x2 = glyph.geom[j * 3 + 1].x * fontSizeX + x;
						float y2 = glyph.geom[j * 3 + 1].y * fontSizeY + glyphAscent + y;

						float x3 = glyph.geom[j * 3 + 2].x * fontSizeX + x;
						float y3 = glyph.geom[j * 3 + 2].y * fontSizeY + glyphAscent + y;

						if (batch.italic) {
							x1 -= y1 * 0.25f;
							x2 -= y2 * 0.25f;
							x3 -= y3 * 0.25f;
						}

						renderer.filledTriangle(x1, y1, x2, y2, x3, y3);
					}

					if (batch.underline) {
						renderer.line(x, glyphAscent, x + glyphAdvance, glyphAscent, 0.75f);
					}

					if (batch.strikethrough) {
						renderer.line(x, glyphAscent * 3f / 4f, x + glyphAdvance, glyphAscent * 3f / 4f, 0.75f);
					}

					x += glyphAdvance;
				}

			}

			y += line.height();
		}
	}

	private Texture createTexture(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);
		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}

	public void render(ShapeBatch shapeBatch, float xStart, float yStart) {
		for (Shape shape : shapeBatch.shapes) {

			if (shape instanceof DrawRect rect) {
				renderer.rectangle(xStart + rect.x, yStart + rect.y, rect.w, rect.h, rect.color, rect.thickness);
			} else if (shape instanceof FillRect rect) {
				float x1 = xStart + rect.w;
				float y1 = yStart;
				float x2 = xStart;
				float y2 = yStart + rect.h;

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
