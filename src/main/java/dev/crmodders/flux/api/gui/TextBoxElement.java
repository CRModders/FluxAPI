package dev.crmodders.flux.api.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import dev.crmodders.flux.util.text.TextEditor;
import finalforeach.cosmicreach.ui.UIElement;

public class TextBoxElement extends UIElement {

	private static TextBoxElement current = null;
	private static InputProcessor prev = null;

	private boolean active;
	private TextEditor editor;
	private String background;

	public TextBoxElement(float x, float y, float w, float h, String background) {
		super(x, y, w, h);
		this.active = false;
		this.editor = new TextEditor();
		this.background = background;
		updateText();
	}

	@Override
	public void onClick() {
		setActive(true);
	}

	public void updateText() {
		StringBuilder text = new StringBuilder(editor);
		if (text.isEmpty() && !isActive()) {
			text.append(background);
		}
		if (isActive()) {
			text.insert(editor.getCursor(), "_");
		}
		super.setText(text.toString());
	}

	@Override
	public void setText(String text) {
		editor.clear();
		for (int i = 0; i < text.length(); i++) {
			editor.append(text.charAt(i));
		}
		updateText();
	}

	public String getText() {
		return editor.toString();
	}

	public void setActive(boolean active) {
		if (active) {
			// deactivite current button
			if (current != null && current != this) {
				current.setActive(false);
			}
			// set prev input processor, set current to this, set current input processor to
			// this
			prev = Gdx.input.getInputProcessor();
			current = this;
			Gdx.input.setInputProcessor(this);
		} else {
			// return input processor, set current to null
			Gdx.input.setInputProcessor(prev);
			prev = null;
			current = null;
		}
		this.active = active;
		updateText();
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (active) {
			switch (keycode) {
			case Input.Keys.LEFT:
				editor.setCursor(editor.getCursor() - 1);
				updateText();
				return true;
			case Input.Keys.RIGHT:
				editor.setCursor(editor.getCursor() + 1);
				updateText();
				return true;
			case Input.Keys.ENTER:
				setActive(false);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean keyTyped(char chr) {
		if (active) {
			boolean ret = editor.append(chr);
			updateText();
			return ret;
		}
		return false;
	}

}
