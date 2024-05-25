package dev.crmodders.flux.ui.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import dev.crmodders.flux.ui.Document;
import dev.crmodders.flux.ui.text.TextBatch;
import dev.crmodders.flux.ui.text.TextBatchBuilder;
import finalforeach.cosmicreach.settings.Keybind;

public class MarkdownDocument implements Document, InputProcessor {

	public TextBatch fontRenderBatch;
	public boolean dirty;

	public boolean editable;
	public StringBuilder editor;
	public int cursor;

	public MarkdownDocument() {
		this.fontRenderBatch = null;
		this.dirty = true;

		this.editable = false;
		this.editor = new StringBuilder();
		this.cursor = 0;
	}

	public void backspace() {
		markDirty();
		if (cursor > 0 && cursor <= editor.length()) {
			editor.deleteCharAt(cursor - 1);
			cursor--;
		}
	}

	public void newline() {
		markDirty();
		editor.insert(cursor, '\n');
		cursor++;
	}

	public boolean type(char c) {
		markDirty();
		if (Keybind.isPrintableChar(c)) {
			editor.insert(cursor, c);
			cursor++;
			return true;
		}
		return false;
	}

	public void moveCursorTo(int to) {
		markDirty();
		if (to < 0) {
			cursor = 0;
		} else cursor = Math.min(to, editor.length());
	}

	public void moveCursorBy(int by) {
		markDirty();
		if (cursor + by < 0) {
			cursor = 0;
		} else if (cursor + by > editor.length()) {
			cursor = editor.length();
		} else {
			cursor += by;
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (!editable)
			return false;
		switch (keycode) {
		case Input.Keys.LEFT:
			moveCursorBy(-1);
			break;
		case Input.Keys.RIGHT:
			moveCursorBy(1);
			break;
		case Input.Keys.HOME:
			moveCursorTo(0);
			break;
		case Input.Keys.END:
			moveCursorTo(editor.length());
			break;
		case Input.Keys.ENTER:
			newline();
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int var1) {
        return false;
	}

	@Override
	public boolean keyTyped(char chr) {
		if (!editable)
			return false;
        if (chr == 8) {
            backspace();
            return true;
        }
        return type(chr);
    }

	@Override
	public boolean touchDown(int var1, int var2, int var3, int var4) {
        return false;
	}

	@Override
	public boolean touchUp(int var1, int var2, int var3, int var4) {
        return false;
	}

	@Override
	public boolean touchCancelled(int var1, int var2, int var3, int var4) {
        return false;
	}

	@Override
	public boolean touchDragged(int var1, int var2, int var3) {
        return false;
	}

	@Override
	public boolean mouseMoved(int var1, int var2) {
        return false;
	}

	@Override
	public boolean scrolled(float var1, float var2) {
        return false;
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	public void markDirty() {
		dirty = true;
	}

	@Override
	public void render(TextBatchBuilder batch) {
//		for (int i = 0; i < editor.length() + 1; i++) {
//			if (i == cursor) {
//				batch.underline(true);
//			}
//			if (i < editor.length()) {
//				batch.add(editor.charAt(i));
//			} else {
//				batch.add(' ');
//			}
//			if (i == cursor) {
//				batch.underline(true);
//			}
//		}
	}

	@Override
	public void setBatch(TextBatch batch) {
		this.fontRenderBatch = batch;
		this.dirty = false;
	}

	@Override
	public TextBatch getBatch() {
		return fontRenderBatch;
	}

}
