package dev.crmodders.flux.api.renderer.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.FloatArray;
import dev.crmodders.flux.font.FontGlyph;
import dev.crmodders.flux.font.Font;

public class StyleBatch {
	public boolean fresh = true;
	public StringBuilder chars = new StringBuilder();

	public Font font;
	public boolean bold;
	public boolean italic;
	public boolean underline;
	public boolean strikethrough;
	public Color color;
	public float alpha;
	public float fontSize;
	public float fontSizeX;
	public float fontSizeY;
	public float[][] pageVertices;
	public float width;

	public StyleBatch(Font font, float fontSize) {
		this.font = font;
		this.bold = false;
		this.italic = false;
		this.underline = false;
		this.strikethrough = false;
		this.color = Color.WHITE;
		this.alpha = 1f;
		this.fontSize = fontSize;
	}

	public StyleBatch(StyleBatch copy, CharSequence chars) {
		this(copy);
		this.chars.append(chars);
	}

	public StyleBatch(StyleBatch copy) {
		this.font = copy.font;
		this.bold = copy.bold;
		this.italic = copy.italic;
		this.underline = copy.underline;
		this.strikethrough = copy.strikethrough;
		this.color = copy.color;
		this.alpha = copy.alpha;
		this.fontSize = copy.fontSize;
	}

	public void calculateVertices() {
		int numberOfPages = font.bitmapFont.getRegions().size;
		FloatArray[] pageVertices = new FloatArray[numberOfPages];
		float floatColor = color.toFloatBits();

		for(int i = 0; i < pageVertices.length; i++) {
			pageVertices[i] = new FloatArray();
		}

		this.fontSizeX = fontSize;
		this.fontSizeY = fontSize;
		if(bold) {
			fontSizeX *= 1.25f;
		}

		float shear = 0f;
		if(italic) {
			shear = 0.5f;
		}

		float x = 0f;
		for(int i = 0; i < chars.length(); i++) {
			char chr = chars.charAt(i);
			FontGlyph glyph = font.glyph(chr);
			addGlyph(pageVertices, glyph, x, 0, shear, floatColor);
			x += glyph.advance * fontSizeX;
		}
		this.width = x + shear;

		this.pageVertices = new float[numberOfPages][];
		for(int i = 0; i < this.pageVertices.length; i++) {
			this.pageVertices[i] = pageVertices[i].items;
		}
	}

	private void addGlyph(FloatArray[] pageVertices, FontGlyph fontGlyph, float x, float y, float shear, float floatColor) {
		BitmapFont.Glyph glyph = fontGlyph.glyph;
		float scaleX = font.bitmapFont.getScaleX() * fontSizeX;
		float scaleY = font.bitmapFont.getScaleY() * fontSizeY;

		float y1 = y + glyph.yoffset * scaleY;
		float x1 = x + glyph.xoffset * scaleX;
		float y2 = y + glyph.yoffset * scaleY + glyph.height * scaleY;
		float x2 = x + glyph.xoffset * scaleX + glyph.width * scaleX;

		final FloatArray vertices = pageVertices[glyph.page];
		vertices.add(x1 + y1 * shear);
		vertices.add(y1);
		vertices.add(floatColor);
		vertices.add(glyph.u);
		vertices.add(glyph.v);

		vertices.add(x1);
		vertices.add(y2);
		vertices.add(floatColor);
		vertices.add(glyph.u);
		vertices.add(glyph.v2);

		vertices.add(x2);
		vertices.add(y2);
		vertices.add(floatColor);
		vertices.add(glyph.u2);
		vertices.add(glyph.v2);

		vertices.add(x2 + y1 * shear);
		vertices.add(y1);
		vertices.add(floatColor);
		vertices.add(glyph.u2);
		vertices.add(glyph.v);
	}

}
