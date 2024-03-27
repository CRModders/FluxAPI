package dev.crmodders.flux.api.renderer.text;

import com.badlogic.gdx.graphics.Color;
import dev.crmodders.flux.font.VectorFont;

public class StyleBatch {
	public boolean fresh = true;
	public StringBuilder chars = new StringBuilder();

	public VectorFont font;
	public boolean bold;
	public boolean italic;
	public boolean underline;
	public boolean strikethrough;
	public Color color;
	public float alpha;
	public float fontSize;

	public StyleBatch(VectorFont font, float fontSize) {
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

}
