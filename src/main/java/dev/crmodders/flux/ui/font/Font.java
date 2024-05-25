package dev.crmodders.flux.ui.font;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Font {

    public static Font generateFontTextureFont(List<FontTexture> fontTextures) {
        int totalSizeInSquarePixels = fontTextures.size() * 256 * 256;
        int width;
        int height = -1;
        for(width = 256; width <= 16384; width += 256) {
            height = totalSizeInSquarePixels / width;
            if(width >= height && Math.floorDiv(height, 256) * 256 == height) {
                break;
            }
        }

        int x = 0;
        int y = 0;

        Map<FontTexture, Point> locations = new HashMap<>();
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        for(FontTexture ft : fontTextures) {
            TextureData td = ft.fontTextureRegions[0].getTexture().getTextureData();
            td.prepare();
            pixmap.drawPixmap(td.consumePixmap(), x, y);
            locations.put(ft, new Point(x, y));
            x += 256;
            if(x >= width) {
                x = 0;
                y += 256;
            }
        }

        Texture texture = new Texture(pixmap);
        BitmapFont.BitmapFontData data = new BitmapFont.BitmapFontData();
        data.flipped = true;
        for(FontTexture ft : fontTextures) {
            int xOffset = locations.get(ft).x;
            int yOffset = locations.get(ft).y;
            for(int unicode = ft.unicodeStart; unicode < ft.unicodeStart + 256; unicode++) {
                Vector2 charStart = ft.fontCharStartPos[unicode - ft.unicodeStart];
                Vector2 charSize = ft.fontCharSizes[unicode - ft.unicodeStart];
                BitmapFont.Glyph glyph = new BitmapFont.Glyph();
                glyph.id = unicode;
                glyph.srcX = xOffset + (int)charStart.x;
                glyph.srcY = yOffset + (int)charStart.y;
                glyph.width = (int)charSize.x;
                if(unicode == ' ')
                    glyph.width /= 4;
                glyph.height = (int)charSize.y;
                glyph.xadvance = glyph.width + 2;
                glyph.yoffset = 16 - glyph.height;
                data.setGlyph(unicode, glyph);

            }

        }

        return new Font(new BitmapFont(data, new TextureRegion(texture), true), 16);
    }

    public static Font generateBitmapFont(FileHandle fnt) {
        BitmapFont bm = new BitmapFont(fnt, true);
        try(BufferedReader reader = new BufferedReader(fnt.reader())) {
            String line = reader.readLine();
            int sizeStart = line.indexOf("size=");
            int sizeEnd = line.indexOf(" ", sizeStart);
            String size = line.substring(sizeStart + 5, sizeEnd);
            return new Font(bm, Integer.parseInt(size));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
