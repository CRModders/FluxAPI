package io.github.crmodders.flux.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import io.github.crmodders.flux.FluxAPI;

public class CustomFontRenderer {
    public Texture fontTexture;
    public TextureRegion[] fontTextureRegions;
    public Vector2[] fontCharStartPos;
    public Vector2[] fontCharSizes;
    private Vector2 tmpTextDim;

    public static CustomFontRenderer INSTANCE = new CustomFontRenderer(1, false);

    public int fontsize;
    public CustomFontRenderer(int fontsize, boolean stripes) {
        this.fontsize = fontsize;

        fontTexture = resize(new Texture("textures/font.png"), this.fontsize, stripes);
        int numCols = 16;
        int numRows = 16;
        TextureRegion[][] tr = TextureRegion.split(fontTexture, fontTexture.getWidth() / numCols, fontTexture.getHeight() / numRows);
        fontTextureRegions = new TextureRegion[tr.length * tr[0].length];
        fontCharStartPos = new Vector2[fontTextureRegions.length];
        fontCharSizes = new Vector2[fontTextureRegions.length];
        TextureData texData = fontTexture.getTextureData();
        Pixmap pix = texData.consumePixmap();

        for(int i = 0; i < numCols; ++i) {
            for(int j = 0; j < numRows; ++j) {
                TextureRegion texReg = tr[j][i];
                int idx = j * numRows + i;
                fontTextureRegions[idx] = texReg;
                int maxBoundsX = texReg.getRegionX();
                int maxBoundsY = texReg.getRegionY();
                int minBoundsX = texReg.getRegionX() + texReg.getRegionWidth();
                int minBoundsY = texReg.getRegionY() + texReg.getRegionHeight();
                boolean isFullyTransparent = true;

                for(int px = texReg.getRegionX(); px < texReg.getRegionX() + texReg.getRegionWidth(); ++px) {
                    for(int py = texReg.getRegionY(); py < texReg.getRegionY() + texReg.getRegionHeight(); ++py) {
                        int pixColor = pix.getPixel(px, py);
                        boolean isFullyTransparentPixel = (pixColor & 255) == 0;
                        if (!isFullyTransparentPixel) {
                            minBoundsX = Math.min(minBoundsX, px);
                            minBoundsY = Math.min(minBoundsY, py);
                            maxBoundsX = Math.max(maxBoundsX, px);
                            maxBoundsY = Math.max(maxBoundsY, py);
                            isFullyTransparent = false;
                        }
                    }
                }

                fontCharStartPos[idx] = new Vector2((float)minBoundsX, (float)minBoundsY);
                fontCharSizes[idx] = new Vector2((float)Math.max(0, maxBoundsX - minBoundsX + 1), (float)Math.max(0, maxBoundsY - minBoundsY + 1));
                if (isFullyTransparent) {
                    fontCharStartPos[idx].set((float)texReg.getRegionX(), (float)texReg.getRegionY());
                    fontCharSizes[idx].set((float)texReg.getRegionWidth(), (float)texReg.getRegionHeight());
                }

                texReg.flip(false, true);
            }
        }

        pix.dispose();

        tmpTextDim = new Vector2();
    }

    public Vector2 getTextDimensions(Viewport uiViewport, String text, Vector2 textDim) {
        float x = 0.0F;
        float y = 0.0F;
        float xOff = 0.0F;
        float yOff = 0.0F;

        for(int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            TextureRegion texReg = fontTextureRegions[c];
            xOff -= fontCharStartPos[c].x % (float)texReg.getRegionWidth();
            switch (c) {
                case '\n':
                    y += (float)texReg.getRegionHeight();
                    x = Math.max(x, xOff);
                    xOff = 0.0F;
                    yOff = 0.0F;
                    break;
                case ' ':
                    xOff += fontCharSizes[c].x / 4.0F;
                    break;
                default:
                    xOff += fontCharSizes[c].x + fontCharStartPos[c].x % (float)texReg.getRegionWidth() + 2.0F;
                    yOff = Math.max(yOff, fontCharSizes[c].y + fontCharStartPos[c].y % (float)texReg.getRegionHeight());
            }
        }

        textDim.set(Math.max(x, xOff), y + yOff);
        return textDim;
    }

    public void drawText(SpriteBatch batch, Viewport uiViewport, String text, float xStart, float yStart, HorizontalAnchor hAnchor, VerticalAnchor vAnchor) {
        Vector2 textDim = getTextDimensions(uiViewport, text, tmpTextDim);
        float w = textDim.x;
        float h = textDim.y;
        switch (hAnchor) {
            case LEFT_ALIGNED:
                xStart -= uiViewport.getWorldWidth() / 2.0F;
                break;
            case RIGHT_ALIGNED:
                xStart = xStart + uiViewport.getWorldWidth() / 2.0F - w;
                break;
            case CENTERED:
            default:
                xStart -= w / 2.0F;
        }

        switch (vAnchor) {
            case TOP_ALIGNED:
                yStart -= uiViewport.getWorldHeight() / 2.0F;
                break;
            case BOTTOM_ALIGNED:
                yStart = yStart + uiViewport.getWorldHeight() / 2.0F - h;
                break;
            case CENTERED:
            default:
                yStart -= h / 2.0F;
        }

        drawText(batch, uiViewport, text, xStart, yStart);
    }

    public void drawText(SpriteBatch batch, Viewport uiViewport, String text, float xStart, float yStart) {
        float x = xStart;
        float y = yStart;

        for(int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            if (c > fontTextureRegions.length) {
                c = '?';
            }

            TextureRegion texReg = fontTextureRegions[c];
            x -= fontCharStartPos[c].x % (float)texReg.getRegionWidth();
            switch (c) {
                case '\n':
                    y += (float)texReg.getRegionHeight();
                    x = xStart;
                    break;
                case ' ':
                    x += fontCharSizes[c].x / 4.0F;
                    break;
                default:
                    batch.draw(texReg, x, y);
                    x += fontCharSizes[c].x + fontCharStartPos[c].x % (float)texReg.getRegionWidth() + 2.0F;
            }
        }

    }

    private Texture resize(Texture oldTexture, int size, boolean stripes) {
        if (!oldTexture.getTextureData().isPrepared()) oldTexture.getTextureData().prepare();
        Pixmap oldPix = oldTexture.getTextureData().consumePixmap();
        Pixmap newPix = new Pixmap(oldPix.getWidth() * size, oldPix.getHeight() * size, oldPix.getFormat());
        for (int x = 0; x < oldPix.getWidth(); x++) {
            int nx = x * size;
            for (int y = 0; y < oldPix.getHeight(); y++) {
                int ny = y * size;
                newPix.drawPixel(nx, ny, oldPix.getPixel(x, y));
                for (int s = 0; s < size; s++) {
                    newPix.drawPixel(nx + s, ny, oldPix.getPixel(x, y));
                    if (!stripes) {
                        for (int s2 = 0; s2 < size; s2++) {
                            newPix.drawPixel(nx + s, ny + s2, oldPix.getPixel(x, y));
                        }
                    }
                }
            }
        }
        FluxAPI.LOGGER.info(String.valueOf(oldPix.getWidth() * size));
        return new Texture(newPix);
    }

}
