package dev.crmodders.flux.ui.image;

import com.badlogic.gdx.graphics.Texture;

public class Image {
    public final Texture texture;
    public final float x, y, width, height, rotation;

    public Image(Texture texture, float x, float y, float width, float height, float rotation) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }
}
