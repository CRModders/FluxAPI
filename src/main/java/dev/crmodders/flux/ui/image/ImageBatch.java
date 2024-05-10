package dev.crmodders.flux.ui.image;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.crmodders.flux.ui.Batch;
import dev.crmodders.flux.ui.UIRenderer;

import java.util.List;

public class ImageBatch implements Batch {
    public final List<Image> images;

    public ImageBatch(List<Image> images) {
        this.images = images;
    }

    @Override
    public void render(UIRenderer uiRenderer, float xStart, float yStart) {
        for (Image image : images) {
            TextureRegion region  = new TextureRegion(image.texture);
            region.setRegion(0f, 1f, 1f, 0f);
            uiRenderer.batch.draw(
                    region,
                    image.x + xStart,
                    image.y + yStart,
                    image.width / 2f,
                    image.height / 2f,
                    image.width,
                    image.height,
                    1f,
                    1f,
                    image.rotation
            );
        }
    }

    @Override
    public float width() {
        return 0; // TODO
    }

    @Override
    public float height() {
        return 0; // TODO
    }
}
