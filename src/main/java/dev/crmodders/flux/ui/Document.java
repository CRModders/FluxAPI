package dev.crmodders.flux.ui;

import dev.crmodders.flux.ui.text.TextBatch;
import dev.crmodders.flux.ui.text.TextBatchBuilder;

public interface Document {

	boolean isDirty();

	void render(TextBatchBuilder batch);

	void setBatch(TextBatch batch);

	TextBatch getBatch();

}
