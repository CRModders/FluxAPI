package dev.crmodders.flux.ui.font;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontGlyph {

    public char chr;
    public float ascent;
    public float advance;
    public BitmapFont.Glyph glyph;

    public FontGlyph(char chr, float ascent, float advance, BitmapFont.Glyph glyph) {
        this.chr = chr;
        this.ascent = ascent;
        this.advance = advance;
        this.glyph = glyph;
    }

}
