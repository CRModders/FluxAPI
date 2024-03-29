package dev.crmodders.flux.font;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.io.IOException;
import java.util.Objects;

public class Font {

    public static Font generateTrueTypeFont(FileHandle ttf, int resolution, String characters) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(ttf);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = resolution;
        parameter.characters = characters;
        parameter.flip = true;
        return new Font(generator.generateFont(parameter), resolution);
    }

    public final BitmapFont bitmapFont;
    public final float ascent;
    private final FontGlyph[] glyphs;

    public Font(BitmapFont font, int size) {
        this.glyphs = new FontGlyph[65536];
        this.bitmapFont = font;
        BitmapFont.BitmapFontData data = bitmapFont.getData();

        data.setScale(1 / (float)size, 1 / (float)size);
        data.missingGlyph = data.getGlyph('#');

        this.ascent = bitmapFont.getAscent();

        for(char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
            BitmapFont.Glyph glyph = Objects.requireNonNullElse(data.getGlyph(c), data.missingGlyph);
            float advance = glyph.xadvance / (float) size;
            glyphs[c] = new FontGlyph(c, ascent, advance, glyph);
        }
    }

    public FontGlyph glyph(char chr) {
        return glyphs[chr];
    }

}
