package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.api.settings.LocaleSetting;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;
import finalforeach.cosmicreach.ui.UIElement;

import java.util.List;
import java.util.Locale;

public class ComboBoxElement<T> extends UIElement {

    private final List<T> elements;
    private int selected;

    public ComboBoxElement(T defaultValue, List<T> elements, TranslationKey textKey) {
        this(0, 0, 0, 0, defaultValue, elements, textKey);
    }

    public ComboBoxElement(float x, float y, float w, float h, T defaultValue, List<T> elements, TranslationKey textKey) {
        super(x, y, w, h, false);
        ((UIElementInterface) this).setTextKey(textKey);
        this.elements = elements;
        this.selected = this.elements.indexOf(defaultValue);
        onCreate();
        updateText();
    }

    @Override
    public void onClick() {
        super.onClick();
        selected++;
        selected %= elements.size();
        updateText();
    }

    public void setSelected(T t) {
        selected = elements.indexOf(t);
    }
    public T getSelected() {
        return elements.get(selected);
    }

    @Override
    public void updateText() {
        super.updateText();
        T selected = getSelected();
        TranslationKey textKey = ((UIElementInterface) this).getTextKey();
        if (textKey != null) {
            TranslationString text = FluxSettings.SelectedLanguage.getTranslatedString(textKey);
            setText(text.format(selected));
        }
    }

}