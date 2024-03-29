package dev.crmodders.flux.font;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.io.IOException;
import java.util.Objects;

public class TrueTypeFont {

    public final BitmapFont bitmapFont;
    public final float ascent;
    private final FontGlyph[] glyphs;

    public TrueTypeFont(FileHandle file, int resolution, String characters) throws IOException {
        this.glyphs = new FontGlyph[65536];

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = resolution;
        parameter.characters = characters;
        parameter.flip = true;
        bitmapFont = generator.generateFont(parameter);
        BitmapFont.BitmapFontData data = bitmapFont.getData();
        data.setScale(1 / (float)resolution, 1 / (float)resolution);
        data.missingGlyph = data.getGlyph('#');

        this.ascent = bitmapFont.getAscent();

        for(char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
            BitmapFont.Glyph glyph = Objects.requireNonNullElse(data.getGlyph(c), data.missingGlyph);
            float advance = glyph.xadvance / (float) resolution;
            glyphs[c] = new FontGlyph(c, ascent, advance, glyph);
        }

    }

    public FontGlyph glyph(char chr) {
        return glyphs[chr];
    }

}
