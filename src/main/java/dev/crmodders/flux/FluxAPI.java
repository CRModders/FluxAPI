package dev.crmodders.flux;

import dev.crmodders.flux.api.config.BasicConfig;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import net.fabricmc.api.ModInitializer;

public class FluxAPI implements ModInitializer {

    @Override
    public void onInitialize() {
        LogWrapper.init();
        LogWrapper.info("Flux Fabric Initialized");
    }

}
