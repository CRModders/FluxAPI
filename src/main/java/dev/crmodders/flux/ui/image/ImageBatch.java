package dev.crmodders.flux.ui.image;

import dev.crmodders.flux.ui.Batch;

import java.util.List;

public class ImageBatch implements Batch {
    public final List<Image> images;

    public ImageBatch(List<Image> images) {
        this.images = images;
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
