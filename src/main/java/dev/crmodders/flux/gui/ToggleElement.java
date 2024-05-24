package dev.crmodders.flux.gui;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.gui.base.BaseButton;
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
		TranslationString text = FluxSettings.SelectedLanguage.get(key);
		TranslationString on = FluxSettings.SelectedLanguage.get(FluxConstants.TextOn);
		TranslationString off = FluxSettings.SelectedLanguage.get(FluxConstants.TextOff);
		return text.format(value ? on.string() : off.string());
	}

}
