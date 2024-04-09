package dev.crmodders.flux.api.renderer.image;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.crmodders.flux.api.renderer.interfaces.Batch;
import dev.crmodders.flux.api.renderer.interfaces.BatchBuilder;

import java.util.ArrayList;
import java.util.List;

public class ImageBatchBuilder implements BatchBuilder {

    public List<Image> images = new ArrayList<>();

    public void drawImage(Texture texture, float x, float y, float w, float h) {
        images.add(new Image(texture, x, y, w, h, 0f));
    }
    public void drawImage(Texture texture, float x, float y, float w, float h, float rotation) {
        images.add(new Image(texture, x, y, w, h, rotation));
    }

    @Override
    public ImageBatch build() {
        return new ImageBatch(images);
    }
}
