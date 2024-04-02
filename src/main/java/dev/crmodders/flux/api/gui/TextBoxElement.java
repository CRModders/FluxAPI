package dev.crmodders.flux.api.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationString;
import dev.crmodders.flux.util.text.TextEditor;
import finalforeach.cosmicreach.gamestates.WorldCreationMenu;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.UITextInput;
import finalforeach.cosmicreach.world.World;

public class TextBoxElement extends UITextInput {

    protected String defaultText;

    private boolean triggerOnCreate = true;

    public TextBoxElement(TranslationKey textKey, String defaultText) {
        this(0, 0, 0, 0, textKey, defaultText);
    }

    public TextBoxElement(float x, float y, float w, float h, TranslationKey textKey, String defaultText) {
        super(x, y, w, h);
        ((UIElementInterface) this).setTextKey(textKey);
        this.defaultText = defaultText;
        this.inputText = getDefaultInputText();
        onCreate();
    }

    @Override
    public void onCreate() {
        if(triggerOnCreate) {
            triggerOnCreate = false;
            return;
        }
        super.onCreate();
    }

    public String getDefaultInputText() {
        return defaultText;
    }

    public void onEnter() {
    }

    @Override
    public void deactivate() {
        super.deactivate();
        onEnter();
    }

    public void setInputText(String text) {
        this.inputText = text;
    }

    public String getInputText() {
        return inputText;
    }

    @Override
    public void updateText() {
        TranslationKey textKey = ((UIElementInterface) this).getTextKey();
        if(textKey != null) {
            this.labelPrefix = textKey.getTranslated().string();
        } else {
            this.labelPrefix = "";
        }
        super.updateText();
    }

}
