package dev.crmodders.flux.menus;

import dev.crmodders.modmenu.api.ConfigScreenFactory;
import dev.crmodders.modmenu.api.ModMenuApi;

public class FluxModmenuConfig implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return FluxOptionMenu::new;
    }
}
