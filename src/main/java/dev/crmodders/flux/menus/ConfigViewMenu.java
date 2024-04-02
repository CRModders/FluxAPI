package dev.crmodders.flux.menus;

import dev.crmodders.flux.api.config.BasicConfig;
import dev.crmodders.flux.api.gui.CustomButtonElement;
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

        addTextElement(0, -200, 72, TEXT_TITLE);
        addDoneButton();

        AccessableRegistry<BasicConfig> configs = (AccessableRegistry<BasicConfig>) FluxRegistries.MOD_CONFIGS;

        int vheight = -100;
        for(Identifier id : configs.getRegisteredNames()) {
            BasicConfig config = configs.get(id);
            CustomButtonElement button = new CustomButtonElement(0, vheight, 250, 25, b -> {
                switchToGameState(new EditConfigMenu(ConfigViewMenu.this, config));
            }, TEXT_BUTTON);
            button.setText(config.getFriendlyName());
            addUIElement(button);
            vheight += 30;
        }

    }

}
