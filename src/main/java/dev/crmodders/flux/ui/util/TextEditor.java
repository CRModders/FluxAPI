package dev.crmodders.flux.ui.util;

import finalforeach.cosmicreach.settings.Keybind;

public class TextEditor implements CharSequence {

	private StringBuilder builder;
	private int cursor;

	public TextEditor() {
		this.builder = new StringBuilder();
		this.cursor = 0;
	}

	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		if (cursor < 0) {
			this.cursor = 0;
		} else this.cursor = Math.min(cursor, builder.length());
	}

	public void clear() {
		this.builder = new StringBuilder();
		this.cursor = 0;
	}

	public void insert(int position, char c) {
		builder.insert(position, c);
	}

	public void delete(int start, int end) {
		builder.delete(start, end);
	}

	public void delete(int index) {
		builder.deleteCharAt(index);
	}

	public void backspace() {
		if (cursor > 0 && cursor <= builder.length()) {
			builder.deleteCharAt(cursor - 1);
			cursor--;
		}
	}

	public boolean type(char c) {
		if (Keybind.isPrintableChar(c)) {
			builder.insert(cursor, c);
			cursor++;
			return true;
		}
		return false;
	}

	public void setText(String text) {
		clear();
		builder.append(text);
	}

	public boolean append(char c) {
        if (c == 8) {
            backspace();
			return true;
        }
        return type(c);
    }

	@Override
	public int length() {
		return builder.length();
	}

	@Override
	public char charAt(int index) {
		return builder.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return builder.subSequence(start, end);
	}

	@Override
	public String toString() {
		return builder.toString();
	}

}
