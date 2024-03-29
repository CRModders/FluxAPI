package dev.crmodders.flux.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import finalforeach.cosmicreach.RuntimeInfo;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.WorldSelectionMenu;
import finalforeach.cosmicreach.settings.Controls;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import dev.crmodders.flux.api.config.BasicConfig;
import org.pmw.tinylog.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigViewMenu extends BasicMainMenu {

    GameState lastState;

    public ConfigViewMenu(GameState lastState) {
        this.lastState = lastState;
    }

    @Override
    public void create() {
        super.create();

        addTextElement(0, -200, 72, "Config Menu", false);
        addUIElement("Back", new UIElement(0, -100, 250, 25) {
            @Override
            public void onClick() {
                super.onClick();
                GameState.switchToGameState(lastState);
            }
        });
        if (!new File(BasicConfig.configDir).exists()) new File(BasicConfig.configDir).mkdirs();

        int vheight = -65;
        for (File file : Objects.requireNonNull(new File(BasicConfig.configDir).listFiles())) {
            if (file.getName().contains(".hjson")) {
                addUIElement(file.getName().split("\\.")[0], new UIElement(0, vheight, 250, 25) {
                    @Override
                    public void onClick() {
                        super.onClick();
                        try {
                            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                                String cmd = "rundll32 url.dll,FileProtocolHandler " + file.getCanonicalPath();
                                Runtime.getRuntime().exec(cmd);
                            }
                            else {
                                Desktop.getDesktop().edit(file);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                vheight += 30;
            }
        }

    }
}
