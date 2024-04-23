package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.api.gui.base.BaseText;
import dev.crmodders.flux.localization.TranslationKey;

public class TextElement extends BaseText {

    public TextElement(TranslationKey textKey) {
        this.translation = textKey;
        updateText();
    }

    public TextElement(String text) {
        this.text = text;
        updateText();
    }

}
