package dev.crmodders.flux.menus;

import dev.crmodders.flux.api.gui.SwitchGameStateButtonElement;
import dev.crmodders.flux.api.gui.CustomButtonElement;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;
import dev.crmodders.flux.api.config.BasicConfig;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigViewMenu extends BasicMainMenu {

    public static final TranslationKey TEXT_TITLE = new TranslationKey("fluxapi:config_menu.title");
    public static final TranslationKey TEXT_BUTTON = new TranslationKey("fluxapi:config_menu.button");

    GameState lastState;

    public ConfigViewMenu(GameState lastState) {
        this.lastState = lastState;
    }

    @Override
    public void create() {
        super.create();

        addTextElement(0, -200, 72, TEXT_TITLE);
        addUIElement(new SwitchGameStateButtonElement(0, -100, 250, 25, () -> lastState));
        if (!new File(BasicConfig.configDir).exists()) new File(BasicConfig.configDir).mkdirs();

        int vheight = -65;
        for (File file : Objects.requireNonNull(new File(BasicConfig.configDir).listFiles())) {
            if (file.getName().contains(".hjson")) {

                CustomButtonElement button = new CustomButtonElement(0, vheight, 250, 25, b -> {
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
                }, TEXT_BUTTON);
                button.setText(file.getName().split("\\.")[0]);
                vheight += 30;
            }
        }

    }
}
