package dev.crmodders.flux.menus;

import com.badlogic.gdx.utils.Array;
import dev.crmodders.flux.api.gui.SwitchGameStateButtonElement;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.MainMenu;

public class AssetLoadingMenu extends BasicMainMenu {

    public static final TranslationKey WAITING_TEXT_TITLE = new TranslationKey("fluxapi:loading_menu.waiting_title");
    public static final TranslationKey FINISHED_TEXT_TITLE = new TranslationKey("fluxapi:loading_menu.finished_title");
    public static final TranslationKey TEXT_BUTTON = new TranslationKey("fluxapi:loading_menu.button");

    @Override
    public void create() {
        super.create();

        addTextElement(0, -200, 42, WAITING_TEXT_TITLE);
    }

    public void regenerate() {
        this.uiElements = new Array<>();

        addTextElement(0, -200, 42, FINISHED_TEXT_TITLE);
        addUIElement(new SwitchGameStateButtonElement(0, -100, 250, 25, () -> new MainMenu(), TEXT_BUTTON));
    }

}
