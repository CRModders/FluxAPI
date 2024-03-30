package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;

public class CustomButtonElement extends ButtonElement {

    private String text;

    public CustomButtonElement(ButtonListener listener, TranslationKey textKey) {
        super(listener, textKey);
        this.text = "";
        updateText();
    }

    public CustomButtonElement(float x, float y, float w, float h, ButtonListener listener, TranslationKey textKey) {
        super(x, y, w, h, listener, textKey);
        this.text = "";
        updateText();
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void updateText() {
        TranslationKey textKey = ((UIElementInterface) this).getTextKey();
        if(textKey != null) {
            TranslationString text = FluxSettings.SelectedLanguage.getTranslatedString(textKey);
            setText(text.format(text));
        }
    }
}
