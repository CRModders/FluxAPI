package dev.crmodders.flux.api.gui;

import com.badlogic.gdx.Gdx;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;
import finalforeach.cosmicreach.settings.GraphicsSettings;
import finalforeach.cosmicreach.ui.UIElement;

public class ToggleElement extends UIElement {

	protected boolean value;

	public ToggleElement(float x, float y, float w, float h, boolean defaultValue, TranslationKey textKey) {
		this(x,y,w,h,defaultValue,true,textKey);
	}

	public ToggleElement(float x, float y, float w, float h, boolean defaultValue, boolean triggerOnCreate, TranslationKey textKey) {
		super(x, y, w, h, triggerOnCreate);
		((UIElementInterface) this).setTextKey(textKey);
		this.value = defaultValue;
	}

	@Override
	public void onClick() {
		super.onClick();
		value = !value;
		this.updateText();
	}

	@Override
	public void updateText() {
		super.updateText();
		TranslationKey textKey = ((UIElementInterface) this).getTextKey();
		if (textKey != null) {
			TranslationString text = FluxSettings.SelectedLanguage.getTranslatedString(textKey);
			TranslationString on = FluxSettings.SelectedLanguage.getTranslatedString(FluxConstants.TextOn);
			TranslationString off = FluxSettings.SelectedLanguage.getTranslatedString(FluxConstants.TextOff);
			setText(text.format(value ? on : off));
		}
	}

}
