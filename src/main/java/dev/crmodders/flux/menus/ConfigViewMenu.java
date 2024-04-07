package dev.crmodders.flux.menus;

import dev.crmodders.flux.api.config.BasicConfig;
import dev.crmodders.flux.api.gui.ButtonElement;
import dev.crmodders.flux.api.gui.TextElement;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.gamestates.GameState;

public class ConfigViewMenu extends BasicMenu {

    public static final TranslationKey TEXT_TITLE = new TranslationKey("fluxapi:config_menu.title");
    public static final TranslationKey TEXT_BUTTON = new TranslationKey("fluxapi:config_menu.button");

    GameState lastState;

    public ConfigViewMenu(GameState lastState) {
        super(lastState);

        TextElement title = new TextElement(TEXT_TITLE);
        title.setPosition(0, -200);
        title.automaticSize = true;
        title.automaticSizePadding = 4f;
        title.fontSize = 72;
        title.backgroundEnabled = false;
        addFluxElement(title);
        addDoneButton();

        AccessableRegistry<BasicConfig> configs = (AccessableRegistry<BasicConfig>) FluxRegistries.MOD_CONFIGS;

        int vheight = -100;
        for(Identifier id : configs.getRegisteredNames()) {
            BasicConfig config = configs.get(id);

            ButtonElement button = new ButtonElement(() -> switchToGameState(new EditConfigMenu(ConfigViewMenu.this, config))) {
                @Override
                public String updateTranslation(TranslationKey key) {
                    return key.getTranslated().format(config.getFriendlyName());
                }
            };
            button.setBounds(0, vheight, 250, 25);
            button.translation = TEXT_BUTTON;
            addFluxElement(button);
            vheight += 30;
        }

    }

}
