package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;
import finalforeach.cosmicreach.settings.FloatSetting;
import finalforeach.cosmicreach.ui.UISlider;

public class FloatSliderElement extends UISlider {

	private FloatSetting setting;

	public FloatSliderElement(float min, float max, FloatSetting setting, TranslationKey textKey) {
		this(0, 0, 0, 0, min, max, setting, textKey);
	}

	public FloatSliderElement(float x, float y, float w, float h, float min, float max, FloatSetting setting, TranslationKey textKey) {
		super(min, max, setting.getValue(), x, y, w, h);
		((UIElementInterface) this).setTextKey(textKey);
		this.setting = setting;
		updateText();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.updateText();
	}

	@Override
	public void onMouseDown() {
		super.onMouseDown();
		this.currentValue = setting.getValue();
		this.updateText();
	}

	@Override
	public void onMouseUp() {
		super.onMouseUp();
		setting.setValue(currentValue);
		this.updateText();
	}

	@Override
	public void validate() {
		super.validate();
		this.updateText();
	}

	@Override
	public void updateText() {
		super.updateText();
		TranslationKey textKey = ((UIElementInterface) this).getTextKey();
		if(textKey!=null) {
			TranslationString text = FluxSettings.SelectedLanguage.getTranslatedString(textKey);
			setText(text.format(String.valueOf(currentValue)));
		}
	}

}
