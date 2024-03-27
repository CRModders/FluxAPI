package dev.crmodders.flux.api.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.renderer.CustomFontRenderer;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;

public class TextElement extends UIElement {
    String text;
    public float x;
    public float y;
    public float w;
    public float h;
    boolean shown;
    boolean hoveredOver;
    public HorizontalAnchor hAnchor;
    public VerticalAnchor vAnchor;
    public static UIElement currentlyHeldElement;
    public boolean isHeld;
    protected Texture buttonTex;
    CustomFontRenderer customFontRenderer;
    int fontsize = 1;

    public TextElement(float x, float y, String text, int fontsize, boolean stripes) {
        super(x, y, 1, 1);
        this.hAnchor = HorizontalAnchor.CENTERED;
        this.vAnchor = VerticalAnchor.CENTERED;
        customFontRenderer = new CustomFontRenderer(fontsize, stripes);
        this.buttonTex = uiPanelTex;
        this.x = x;
        this.y = y;
        this.w = 1;
        this.h = 1;
        this.text = text;
        this.fontsize = fontsize;
        this.onCreate();
    }

    float getDisplayX(Viewport uiViewport) {
        switch (this.hAnchor) {
            case LEFT_ALIGNED:
                return this.x - uiViewport.getWorldWidth() / 2.0F;
            case RIGHT_ALIGNED:
                return this.x + uiViewport.getWorldWidth() / 2.0F - this.w;
            case CENTERED:
            default:
                return this.x - this.w / 2.0F;
        }
    }

    float getDisplayY(Viewport uiViewport) {
        switch (this.vAnchor) {
            case TOP_ALIGNED:
                return this.y - uiViewport.getWorldHeight() / 2.0F;
            case BOTTOM_ALIGNED:
                return this.y + uiViewport.getWorldHeight() / 2.0F - this.h;
            case CENTERED:
            default:
                return this.y - this.h / 2.0F;
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void show() {
        this.shown = true;
    }

    public void hide() {
        this.shown = false;
    }

    public boolean isHoveredOver(Viewport viewport, float x, float y) {
        float dx = this.getDisplayX(viewport);
        float dy = this.getDisplayY(viewport);
        return x >= dx && y >= dy && x < dx + this.w && y < dy + this.h;
    }

    public void drawBackground(Viewport uiViewport, SpriteBatch batch, float mouseX, float mouseY) {
        if (this.shown) {
            this.buttonTex = uiPanelTex;
            currentlyHeldElement = null;
            this.drawElementBackground(uiViewport, batch);

        }
    }

    public void drawElementBackground(Viewport uiViewport, SpriteBatch batch) {
        float x = this.getDisplayX(uiViewport);
        float y = this.getDisplayY(uiViewport);
//        if (currentlyHeldElement != this && (!this.hoveredOver || currentlyHeldElement != null)) {
//            batch.draw(uiPanelBoundsTex, x, y, 0.0F, 0.0F, this.w, this.h, 1.0F, 1.0F, 0.0F, 0, 0, this.buttonTex.getWidth(), this.buttonTex.getHeight(), false, true);
//        } else {
//            batch.draw(uiPanelHoverBoundsTex, x, y, 0.0F, 0.0F, this.w, this.h, 1.0F, 1.0F, 0.0F, 0, 0, this.buttonTex.getWidth(), this.buttonTex.getHeight(), false, true);
//        }

        batch.draw(
                this.buttonTex,
                x + 1.0F,
                y + 1.0F,
                1.0F,
                1.0F,
                this.w - 2.0F,
                this.h - 2.0F,
                1.0F,
                1.0F,
                0.0F,
                0,
                0,
                this.buttonTex.getWidth(),
                this.buttonTex.getHeight(),
                false,
                true
        );
    }

    public void onCreate() {
    }

    public void onClick() {
    }

    public void onMouseDown() {
    }

    public void onMouseUp() {
    }

    public void drawText(Viewport uiViewport, SpriteBatch batch) {
        if (this.shown && this.text != null && this.text.length() != 0) {
            UIElement uiElement = this;
            float x = this.getDisplayX(uiViewport);
            float y = this.getDisplayY(uiViewport);
            float maxX = x;
            float maxY = y;

            for(int i = 0; i < this.text.length(); ++i) {
                char c = this.text.charAt(i);
                if (c > customFontRenderer.fontTextureRegions.length) {
                    c = '?';
                }

                TextureRegion texReg = customFontRenderer.fontTextureRegions[c];
                x -= customFontRenderer.fontCharStartPos[c].x % (float)texReg.getRegionWidth();
                switch (c) {
                    case '\n':
                        y += (float)texReg.getRegionHeight();
                        x = uiElement.x;
                        maxX = Math.max(maxX, x);
                        maxY = Math.max(maxY, y);
                        break;
                    case ' ':
                        x += customFontRenderer.fontCharSizes[c].x / 4.0F;
                        maxX = Math.max(maxX, x);
                        break;
                    default:
                        x += customFontRenderer.fontCharSizes[c].x + customFontRenderer.fontCharStartPos[c].x % (float)texReg.getRegionWidth() + 2.0F;
                        maxX = Math.max(maxX, x);
                        maxY = Math.max(maxY, y + (float)texReg.getRegionHeight());
                }
            }

            x = this.getDisplayX(uiViewport);
            y = this.getDisplayY(uiViewport);
            x += this.w / 2.0F - (maxX - x) / 2.0F;
            y += this.h / 2.0F - (maxY - y) / 2.0F;
            customFontRenderer.drawText(batch, uiViewport, this.text, x, y);
        }
    }

    public void updateText() {
    }

    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    static {
        onHoverSound = Gdx.audio.newSound(GameAssetLoader.loadAsset("sounds/ui/e-button-hover.ogg"));
        onClickSound = Gdx.audio.newSound(GameAssetLoader.loadAsset("sounds/ui/e-button-click.ogg"));
    }
}
