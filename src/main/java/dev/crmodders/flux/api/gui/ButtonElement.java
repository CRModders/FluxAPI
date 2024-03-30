package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;
import finalforeach.cosmicreach.ui.UIElement;

public class ButtonElement extends UIElement {
	public static interface ButtonListener {
		void click(ButtonElement button);
	}

	private ButtonListener listener;

	public ButtonElement(ButtonListener listener, TranslationKey textKey) {
		this(0, 0, 0, 0, listener, textKey);
	}

	public ButtonElement(float x, float y, float w, float h, ButtonListener listener, TranslationKey textKey) {
		super(x, y, w, h, false);
		((UIElementInterface) this).setTextKey(textKey);
		this.listener = listener;
		onCreate();
	}

	@Override
	public void onClick() {
		super.onClick();
		listener.click(this);
	}

	@Override
	public void updateText() {
		super.updateText();
		TranslationKey textKey = ((UIElementInterface) this).getTextKey();
		if(textKey != null) {
			TranslationString text = FluxSettings.SelectedLanguage.getTranslatedString(textKey);
			setText(text.string());
		}
	}
}
