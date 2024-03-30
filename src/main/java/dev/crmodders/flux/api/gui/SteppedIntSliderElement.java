package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;
import finalforeach.cosmicreach.settings.IntSetting;
import finalforeach.cosmicreach.ui.UISlider;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class SteppedIntSliderElement extends UISlider {

	private IntSetting setting;
	private int[] values;

	public SteppedIntSliderElement(int min, int max, int[] values, IntSetting setting, TranslationKey textKey) {
		this(0, 0, 0, 0, min, max, values, setting, textKey);
	}

	public SteppedIntSliderElement(float x, float y, float w, float h, int min, int max, int[] values, IntSetting setting, TranslationKey textKey) {
		super(min, max, setting.getValue(), x, y, w, h);
		((UIElementInterface) this).setTextKey(textKey);
		this.setting = setting;
		this.values = values;
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
		setting.setValue((int) currentValue);
		this.updateText();
	}

	@Override
	public void validate() {
		super.validate();
		int n = (int) this.currentValue;
		int c = Arrays.stream(values).boxed().min(Comparator.comparingInt(i -> Math.abs(i - n))).orElseThrow(() -> new NoSuchElementException("No value present"));
		this.currentValue = c;
		this.updateText();
	}

	@Override
	public void updateText() {
		super.updateText();
		TranslationKey textKey = ((UIElementInterface) this).getTextKey();
		if (textKey != null) {
			TranslationString text = FluxSettings.SelectedLanguage.getTranslatedString(textKey);
			setText(text.format(String.valueOf((int)currentValue)));
		}
	}

}
