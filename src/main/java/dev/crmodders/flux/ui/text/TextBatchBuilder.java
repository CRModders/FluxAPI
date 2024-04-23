package dev.crmodders.flux.ui.text;

import com.badlogic.gdx.graphics.Color;
import dev.crmodders.flux.ui.BatchBuilder;
import dev.crmodders.flux.ui.font.Font;
import dev.crmodders.flux.ui.util.StyleStringParser;

import java.util.ArrayList;
import java.util.List;

public class TextBatchBuilder implements BatchBuilder {

	public List<TextLine> lines;
	public TextLine line;
	public StyleBatch batch;

	public TextBatchBuilder(Font font, float fontSize) {
		this.lines = new ArrayList<>();
		this.line = new TextLine();
		this.batch = new StyleBatch(font, fontSize);
	}

	private void makeFreshBatch() {
		if (batch.fresh)
			return;
		line.append(batch);
		batch = new StyleBatch(batch);
	}

	public void font(Font font) {
		makeFreshBatch();
		batch.font = font;
	}

	public void fontSize(float fontSize) {
		makeFreshBatch();
		batch.fontSize = fontSize;
	}

	public void color(Color color) {
		makeFreshBatch();
		batch.color = color;
	}

	public void alpha(float alpha) {
		makeFreshBatch();
		batch.alpha = alpha;
	}

	public void bold(boolean bold) {
		makeFreshBatch();
		batch.bold = bold;
	}

	public void italic(boolean italic) {
		makeFreshBatch();
		batch.italic = italic;
	}

	public void underline(boolean underline) {
		makeFreshBatch();
		batch.underline = underline;
	}

	public void strikethrough(boolean strikethrough) {
		makeFreshBatch();
		batch.strikethrough = strikethrough;
	}

	public void newline() {
		batch.fresh = false;
		makeFreshBatch();
		lines.add(line);
		line = new TextLine();
	}

	public void style(String style) {
		StyleStringParser.parse(this, style, true);
	}

	public void style(String style, boolean removeStyleChars) {
		StyleStringParser.parse(this, style, removeStyleChars);
	}

	public void append(CharSequence seq) {
		for (int i = 0; i < seq.length(); i++) {
			append(seq.charAt(i));
		}
	}

	public void append(char chr) {
		if (chr == '\n') {
			newline();
		} else {
			batch.fresh = false;
			batch.chars.append(chr);
		}
	}

	@Override
	public TextBatch build() {
		newline();
		return new TextBatch(lines);
	}

}