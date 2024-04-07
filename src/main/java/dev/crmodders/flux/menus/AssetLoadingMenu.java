package dev.crmodders.flux.menus;

import dev.crmodders.flux.api.gui.TextElement;
import dev.crmodders.flux.localization.TranslationKey;

public class AssetLoadingMenu extends BasicMenu {

    public static final TranslationKey WAITING_TEXT_TITLE = new TranslationKey("fluxapi:loading_menu.waiting_title");

    public AssetLoadingMenu() {
        super(null);
    }

    @Override
    public void create() {
        super.create();

        TextElement element = new TextElement(WAITING_TEXT_TITLE);
        element.setPosition(0, 0);
        element.automaticSize = true;
        element.automaticSizePadding = 4f;
        element.fontSize = 42;
        element.backgroundEnabled = false;
        addFluxElement(element);

    }

}
