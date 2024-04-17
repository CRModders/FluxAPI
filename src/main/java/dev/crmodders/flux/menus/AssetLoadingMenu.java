package dev.crmodders.flux.menus;

import com.badlogic.gdx.graphics.Texture;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.api.v5.gui.ProgressBarElement;
import dev.crmodders.flux.api.v5.gui.TextElement;
import dev.crmodders.flux.api.v5.gui.base.BaseButton;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;

public class AssetLoadingMenu extends BasicMenu {

    public ProgressBarElement ram;

    public ProgressBarElement major;
    public ProgressBarElement minor;

    public BaseButton icon;

    public AssetLoadingMenu() {
        super(null);
    }

    @Override
    public void create() {
        super.create();

        TextElement element = new TextElement(new TranslationKey("fluxapi:loading_menu.waiting_title"));
        element.setPosition(0, -100);
        element.automaticSize = true;
        element.automaticSizePadding = 4f;
        element.fontSize = 42;
        element.backgroundEnabled = false;
        addFluxElement(element);

        ram = new ProgressBarElement();
        ram.setBounds(0, -250, 500, 40);
        ram.prefixTranslation = false;
        ram.translation = new TranslationKey("fluxapi:loading_menu.ram_usage");
        addFluxElement(ram);

        major = new ProgressBarElement();
        major.setBounds(0, -40, 500, 40);
        major.hideIfZero = true;
        addFluxElement(major);

        minor = new ProgressBarElement();
        minor.setBounds(0, 20, 500, 40);
        minor.hideIfZero = true;
        addFluxElement(minor);

        icon = new BaseButton();
        icon.setBounds(5, 5, 100, 100);
        icon.setAnchors(HorizontalAnchor.RIGHT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
        icon.image = new Texture(FluxConstants.CRModdersIcon.load());
        icon.iconSize = 75;
        icon.iconPadding = 0;
        icon.backgroundEnabled = false;
        addFluxElement(icon);

    }

}
