package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.base.BaseButton;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;

public class ToggleElement extends BaseButton {

	protected boolean value;

	public ToggleElement() {
		this(false);
	}

	public ToggleElement(boolean value) {
		this.value = value;
	}

	@Override
	public void onMouseReleased() {
		super.onMouseReleased();
		this.value = !value;
		super.updateText();
	}

	@Override
	public String updateTranslation(TranslationKey key) {
		TranslationString text = FluxSettings.SelectedLanguage.getTranslatedString(key);
		TranslationString on = FluxSettings.SelectedLanguage.getTranslatedString(FluxConstants.TextOn);
		TranslationString off = FluxSettings.SelectedLanguage.getTranslatedString(FluxConstants.TextOff);
		return text.format(value ? on : off);
	}

}
