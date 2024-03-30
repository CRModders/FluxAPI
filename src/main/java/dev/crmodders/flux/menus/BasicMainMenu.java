package dev.crmodders.flux.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.UIElement;
import dev.crmodders.flux.api.gui.TextElement;

public class BasicMainMenu extends GameState {

    @Override
    public void create() {
        super.create();
    }

    public void addUIElement(UIElement element) {
        element.show();
        this.uiElements.add(element);
    }

    public void addTextElement(int x, int y, float fontSize, TranslationKey textKey) {
        TextElement element = new TextElement(x, y, 0, 0, textKey);
        ((UIElementInterface) element).setFontSize(fontSize);
        element.show();
        this.uiElements.add(element);
    }

    @Override
    public void render(float partTick) {
        super.render(partTick);
        ScreenUtils.clear(0.0F, 0.0F, 0.0F, 1.0F, true);
        Gdx.gl.glEnable(2929);
        Gdx.gl.glDepthFunc(513);
        Gdx.gl.glEnable(2884);
        Gdx.gl.glCullFace(1029);
        Gdx.gl.glEnable(3042);
        Gdx.gl.glBlendFunc(770, 771);
        this.drawUIElements();
    }
}
