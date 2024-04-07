package dev.crmodders.flux.menus;

import org.coolcosmos.modmenu.api.ConfigScreenFactory;
import org.coolcosmos.modmenu.api.ModMenuApi;

public class FluxModmenuConfig implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return FluxOptionMenu::new;
    }
}
