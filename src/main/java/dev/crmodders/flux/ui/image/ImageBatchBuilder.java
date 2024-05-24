package dev.crmodders.flux.ui.image;

import com.badlogic.gdx.graphics.Texture;
import dev.crmodders.flux.ui.UIBatchBuilder;

import java.util.ArrayList;
import java.util.List;

public class ImageBatchBuilder implements UIBatchBuilder {

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
