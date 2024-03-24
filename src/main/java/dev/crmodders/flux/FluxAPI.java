package dev.crmodders.flux;

import dev.crmodders.flux.registry.StableRegistries;
import net.fabricmc.api.ModInitializer;

public class FluxAPI implements ModInitializer {

    @Override
    public void onInitialize() {
        FluxConstants.LOGGER.info("FluxAPI initialized!");
    }

}
