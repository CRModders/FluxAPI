package dev.crmodders.flux.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import dev.crmodders.flux.api.gui.TextElement;
import dev.crmodders.flux.api.gui.base.BaseText;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import finalforeach.cosmicreach.settings.SoundSettings;
import finalforeach.cosmicreach.ui.UIElement;
import org.lwjgl.system.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class ScrollMenu extends BasicMenu implements InputProcessor {

    protected final List<BaseText> elements;
    private float currentIndex;
    private float targetIndex;
    public boolean playSound = true;
    private int lastIndex;

    public ScrollMenu(GameState previousState) {
        super(previousState);
        Gdx.input.setInputProcessor(this);
        elements = new ArrayList<>();
        currentIndex = 0;
        targetIndex = 0;
    }

    public ScrollMenu() {
        this(null);
    }

    public int getSelectedIndex() {
        float distance = targetIndex - currentIndex;
        if(Math.abs(distance) < 0.5) {
            return (int) targetIndex;
        }
        return MathUtils.clamp(Math.round(currentIndex), 0, elements.size()-1);
    }

    public void setSelectedIndex(int index) {
        targetIndex = MathUtils.clamp(index, 0, elements.size()-1);
        currentIndex = targetIndex;
    }

    public void addScrollElement(BaseText element) {
        elements.add(element);
        addFluxElement(element);
    }

    public void removeScrollElement(BaseText element) {
        elements.remove(element);
        removeFluxElement(element);
    }

    @Override
    public void render(float partTime) {
        float distance = targetIndex - currentIndex;
        float increment = distance *  0.05f;
        if(Math.abs(increment) < 0.001) {
            currentIndex = targetIndex;
        } else {
            currentIndex += increment;
        }
        for(int index = 0; index < elements.size(); index++) {
            float distance2 = index - currentIndex;
            float y = distance2 * 75f;
            float inverseDistance = 1f;
            float distance3 = Math.abs(distance2) * 1.35f;
            if(distance3 > 1) {
                inverseDistance = 1f / distance3;
            }
            float size = Math.max(0.6f, inverseDistance);
            BaseText button = elements.get(index);
            button.x = 0;
            button.y = y - 50f;
            button.width = 250f * size;
            button.height = 50f * size;
            button.fontSize = 18f * size;
            button.visible = Math.abs(distance2) < 3.25f;
            button.updateText();
        }
        int index = getSelectedIndex();
        if(index != lastIndex) {
            if(playSound && SoundSettings.isSoundEnabled()) {
                UIElement.onHoverSound.play();
            }
            lastIndex = index;
        }
        super.render(partTime);
    }

    @Override
    public boolean keyDown(int i) {
        if(i == Input.Keys.DOWN) {
            return scrolled(0, 1);
        }
        if(i == Input.Keys.UP) {
            return scrolled(0, -1);
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
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
    public boolean scrolled(float x, float y) {
        targetIndex += y;
        targetIndex = (int) targetIndex;
        targetIndex = Math.max(targetIndex, 0);
        targetIndex = Math.min(targetIndex, elements.size() - 1);
        return true;
    }
}
