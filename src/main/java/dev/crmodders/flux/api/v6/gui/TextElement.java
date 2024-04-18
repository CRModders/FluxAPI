package dev.crmodders.flux.api.v6.gui;

import dev.crmodders.flux.api.v6.gui.base.BaseText;
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