package dev.crmodders.flux.api.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.api.renderer.CustomFontRenderer;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;

public class TextElement extends UIElement {

    public TextElement(float x, float y, String text) {
        this(x, y, 0, 0, text);
        UIElementInterface uiInterface = (UIElementInterface) this;
        uiInterface.setAutomaticSize(true);
    }

    public TextElement(float x, float y, float w, float h, String text) {
        super(x, y, w, h);
        setText(text);
        UIElementInterface uiInterface = (UIElementInterface) this;
        uiInterface.setSoundEnabled(false);
        uiInterface.setActive(false);
        uiInterface.setBorderEnabled(false);
    }
}
