package dev.crmodders.flux;

import dev.crmodders.flux.menus.FluxOptionMenu;
import dev.crmodders.modmenu.api.ConfigScreenFactory;
import dev.crmodders.modmenu.api.ModMenuApi;

public class FluxModMenuConfig implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return FluxOptionMenu::new;
    }
}
