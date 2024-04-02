package dev.crmodders.flux.api.gui;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;
import finalforeach.cosmicreach.gamestates.WorldCreationMenu;
import finalforeach.cosmicreach.ui.UIElement;

public class TextElement extends UIElement {

    public TextElement(TranslationKey textKey) {
        this(0, 0, 0, 0, textKey);
        UIElementInterface uiInterface = (UIElementInterface) this;
        uiInterface.setAutomaticSize(true);
    }

    public TextElement(float x, float y, float w, float h, TranslationKey textKey) {
        super(x, y, w, h, false);
        UIElementInterface uiInterface = (UIElementInterface) this;
        uiInterface.setTextKey(textKey);
        uiInterface.setSoundEnabled(false);
        uiInterface.setActive(false);
        uiInterface.setBorderEnabled(false);
        onCreate();
        updateText();
    }

    @Override
    public void updateText() {
        super.updateText();
        TranslationKey textKey = ((UIElementInterface) this).getTextKey();
        if (textKey != null) {
            TranslationString text = FluxSettings.SelectedLanguage.getTranslatedString(textKey);
            setText(text.string());
        }
    }
}
