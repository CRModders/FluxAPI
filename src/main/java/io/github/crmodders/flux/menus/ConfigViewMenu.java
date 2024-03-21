package io.github.crmodders.flux.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import finalforeach.cosmicreach.RuntimeInfo;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.settings.Controls;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import io.github.crmodders.flux.ui.TextElement;

public class ConfigViewMenu extends BasicMainMenu {

    GameState lastState;

    public ConfigViewMenu(GameState lastState) {
        this.lastState = lastState;
    }

    @Override
    public void create() {
        super.create();

        addTextElement(0, -200, 4, "Config Menu", false);
        addUIElement("test", new UIElement(0, 0, 100, 100));

    }

    public void render() {
        super.render();
        ScreenUtils.clear(0.0F, 0.0F, 0.0F, 1.0F, true);
        Gdx.gl.glEnable(2929);
        Gdx.gl.glDepthFunc(513);
        Gdx.gl.glEnable(2884);
        Gdx.gl.glCullFace(1029);
        Gdx.gl.glEnable(3042);
        Gdx.gl.glBlendFunc(770, 771);
        batch.setProjectionMatrix(this.uiCamera.combined);
        batch.begin();
        float scale = 4.0F;
        float logoW = 192.0F;
        float logoH = 64.0F;
        float logoX = -scale * logoW / 2.0F;
        float logoY = -this.uiViewport.getWorldHeight() / 2.0F;
//        batch.draw(textLogo, logoX, logoY, 0.0F, 0.0F, logoW, logoH, scale, scale, 0.0F, 0, 0, textLogo.getWidth(), textLogo.getHeight(), false, true);
        Vector2 promoTextDim = new Vector2();
        float y = -8.0F;
        String promoText = "Watch the devlogs at youtube.com/@finalforeach";
        FontRenderer.getTextDimensions(this.uiViewport, promoText, promoTextDim);
        batch.setColor(Color.GRAY);
        FontRenderer.drawText(batch, this.uiViewport, promoText, -7.0F, y + 1.0F, HorizontalAnchor.RIGHT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
        batch.setColor(Color.WHITE);
        FontRenderer.drawText(batch, this.uiViewport, promoText, -8.0F, y, HorizontalAnchor.RIGHT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
        String macWarning;
        if (Controls.controllers.size > 0) {
            macWarning = Controls.controllers.size == 1 ? "Controller" : "Controllers";
            String controllerWarning = macWarning + " detected!\nController support is incomplete now,\nand will be improved in later updates.";
            FontRenderer.drawText(batch, this.uiViewport, controllerWarning, 8.0F, y, HorizontalAnchor.LEFT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
        }

        if (RuntimeInfo.isMac()) {
            macWarning = "WARNING: Mac is not supported at this time. The game is unlikely to work.";
            FontRenderer.drawText(batch, this.uiViewport, macWarning, 8.0F, y, HorizontalAnchor.CENTERED, VerticalAnchor.CENTERED);
        }

        y -= promoTextDim.y + 2.0F;
        promoText = "finalforeach.com";
        FontRenderer.getTextDimensions(this.uiViewport, promoText, promoTextDim);
        batch.setColor(Color.GRAY);
        FontRenderer.drawText(batch, this.uiViewport, promoText, -7.0F, y + 1.0F, HorizontalAnchor.RIGHT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
        batch.setColor(Color.WHITE);
        FontRenderer.drawText(batch, this.uiViewport, promoText, -8.0F, y, HorizontalAnchor.RIGHT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
        y -= promoTextDim.y;
        promoText = "Cosmic Reach Version Pre-alpha-" + RuntimeInfo.version;
        FontRenderer.getTextDimensions(this.uiViewport, promoText, promoTextDim);
        batch.setColor(Color.GRAY);
        FontRenderer.drawText(batch, this.uiViewport, promoText, -7.0F, y + 1.0F, HorizontalAnchor.RIGHT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
        batch.setColor(Color.WHITE);
        FontRenderer.drawText(batch, this.uiViewport, promoText, -8.0F, y, HorizontalAnchor.RIGHT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
        float var10000 = y - promoTextDim.y;
        batch.end();
        this.drawUIElements();
    }
}
