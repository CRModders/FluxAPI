package dev.crmodders.flux.api.renderer.image;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Image {
    public final Texture texture;
    public final float x, y, width, height;

    public Image(Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
