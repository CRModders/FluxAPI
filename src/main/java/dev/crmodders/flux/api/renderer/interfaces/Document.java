package dev.crmodders.flux.api.renderer.interfaces;

import dev.crmodders.flux.api.renderer.text.TextBatch;
import dev.crmodders.flux.api.renderer.text.TextBatchBuilder;

public interface Document {

	boolean isDirty();

	void render(TextBatchBuilder batch);

	void setBatch(TextBatch batch);

	TextBatch getBatch();

}
