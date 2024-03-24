package io.github.crmodders.flux;

import net.fabricmc.api.ModInitializer;

public class FluxAPI implements ModInitializer {

    @Override
    public void onInitialize() {
        FluxConstants.LOGGER.info("FluxAPI initialized!");
    }

}
