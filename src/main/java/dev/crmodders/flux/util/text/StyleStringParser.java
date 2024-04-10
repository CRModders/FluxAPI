package dev.crmodders.flux.util.text;

import com.badlogic.gdx.graphics.Color;
import dev.crmodders.flux.ui.text.TextBatchBuilder;

import java.util.HashMap;
import java.util.Map;

public class StyleStringParser {

	private static Map<Character, Color> colors = new HashMap<>();
	static {
		colors.put('0', new Color(0.0f, 0.0f, 0.0f, 1.0f));
		colors.put('1', new Color(0.0f, 0.0f, 0.667f, 1.0f));
		colors.put('2', new Color(0.0f, 0.667f, 0.0f, 1.0f));
		colors.put('3', new Color(0.0f, 0.667f, 0.667f, 1.0f));
		colors.put('4', new Color(0.667f, 0.0f, 0.0f, 1.0f));
		colors.put('5', new Color(0.667f, 0.0f, 0.667f, 1.0f));
		colors.put('6', new Color(1.0f, 0.667f, 0.0f, 1.0f));
		colors.put('7', new Color(0.667f, 0.667f, 0.667f, 1.0f));
		colors.put('8', new Color(0.333f, 0.333f, 0.333f, 1.0f));
		colors.put('9', new Color(0.333f, 0.333f, 1.0f, 1.0f));
		colors.put('a', new Color(0.333f, 1.0f, 0.333f, 1.0f));
		colors.put('b', new Color(0.333f, 1.0f, 1.0f, 1.0f));
		colors.put('c', new Color(1.0f, 0.333f, 0.333f, 1.0f));
		colors.put('d', new Color(1.0f, 0.333f, 1.0f, 1.0f));
		colors.put('e', new Color(1.0f, 1.0f, 0.333f, 1.0f));
		colors.put('f', new Color(1.0f, 1.0f, 1.0f, 1.0f));
	}

	public static void parse(TextBatchBuilder batch, String text, boolean removeStyleChars) {
		for (int i = 0; i < text.length(); i++) {
			char curr = text.charAt(i);
			if (curr == '%' && i + 1 < text.length()) {

				char next = text.charAt(i + 1);
				if (next == '%') {
					//batch.append('%');
					i++;
					continue;
				}
				if(!removeStyleChars) {
					//batch.append(curr);
					//batch.append(next);
				}
				if (next == 'U') {
					batch.underline(true);
					i++;
					continue;
				}
				if (next == 'u') {
					batch.underline(false);
					i++;
					continue;
				}
				if (next == 'S') {
					batch.strikethrough(true);
					i++;
					continue;
				}
				if (next == 's') {
					batch.strikethrough(false);
					i++;
					continue;
				}
				if (next == 'B') {
					batch.bold(true);
					i++;
					continue;
				}
				if (next == 'b') {
					batch.bold(false);
					i++;
					continue;
				}
				if (next == 'I') {
					batch.italic(true);
					i++;
					continue;
				}
				if (next == 'i') {
					batch.italic(false);
					i++;
					continue;
				}
				if (next == '#' && i + 8 < text.length()) {
					try {
						String hex = text.substring(i + 2, i + 8);
						if(!removeStyleChars) {
							//batch.append(hex);
						}
						float r = Integer.parseInt(hex.substring(0, 2), 16) / 255f;
						float g = Integer.parseInt(hex.substring(2, 4), 16) / 255f;
						float b = Integer.parseInt(hex.substring(4, 6), 16) / 255f;
						batch.color(new Color(r, g, b, 1.0f));
						i += 7;
					} catch (Exception e) {
						batch.append('%');
					}
					continue;
				}
				if (colors.containsKey(next)) {
					batch.color(colors.get(next));
					i++;
					continue;
				}

			}
			batch.append(text.charAt(i));
		}
	}

}
