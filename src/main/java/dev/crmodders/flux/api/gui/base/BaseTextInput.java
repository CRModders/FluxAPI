package dev.crmodders.flux.api.gui.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.util.text.TextEditor;
import finalforeach.cosmicreach.ui.UITextInput;

public class BaseTextInput extends BaseButton implements InputProcessor {

    private static BaseTextInput current = null;
    private static InputProcessor prev = null;

    private boolean active;
    protected TextEditor editor;
    public String label;

    public BaseTextInput()  {
        this.active = false;
        this.editor = new TextEditor();
        onLoadString();
    }

    protected void onLoadString() {
    }

    protected void onSaveString() {
    }

    @Override
    public String updateTranslation(TranslationKey key) {
        StringBuilder text = new StringBuilder(editor);
        if(active) {
            text.insert(editor.getCursor(), "_");
        }

        if(key == null) {
            text.insert(0, label);
            return text.toString();
        } else {
            return key.getTranslated().format(text.toString());
        }
    }

    @Override
    public void onMousePressed() {
        super.onMousePressed();
        activate();
    }

    public void activate() {
        if (current != null && current != this) {
            current.deactivate();
        }
        prev = Gdx.input.getInputProcessor();
        current = this;
        Gdx.input.setInputProcessor(this);
        this.active = true;
        onLoadString();
        updateText();
    }

    @Override
    public void deactivate() {
        Gdx.input.setInputProcessor(prev);
        prev = null;
        current = null;
        this.active = false;
        onSaveString();
        updateText();
    }


    @Override
    public boolean keyDown(int keycode) {
        if (active) {
            switch (keycode) {
                case Input.Keys.LEFT:
                    editor.setCursor(editor.getCursor() - 1);
                    updateText();
                    return true;
                case Input.Keys.RIGHT:
                    editor.setCursor(editor.getCursor() + 1);
                    updateText();
                    return true;
                case Input.Keys.ENTER:
                    deactivate();
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char chr) {
        if (active) {
            boolean ret = editor.append(chr);
            updateText();
            return ret;
        }
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }


}
