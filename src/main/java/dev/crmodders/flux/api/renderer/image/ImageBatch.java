package dev.crmodders.flux.api.renderer.image;

import dev.crmodders.flux.api.gui.base.BaseText;
import dev.crmodders.flux.api.renderer.interfaces.Batch;

import java.util.ArrayList;
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
