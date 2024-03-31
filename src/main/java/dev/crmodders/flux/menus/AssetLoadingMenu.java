package dev.crmodders.flux.menus;

import com.badlogic.gdx.utils.Array;
import dev.crmodders.flux.api.gui.SwitchGameStateButtonElement;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.MainMenu;

public class AssetLoadingMenu extends BasicMenu {

    public static final TranslationKey WAITING_TEXT_TITLE = new TranslationKey("fluxapi:loading_menu.waiting_title");

    public AssetLoadingMenu() {
        super(null);
    }

    @Override
    public void create() {
        super.create();

        addTextElement(0, 0, 42, WAITING_TEXT_TITLE);
    }

}
