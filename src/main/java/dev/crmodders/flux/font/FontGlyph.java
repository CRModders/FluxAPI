package dev.crmodders.flux.font;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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