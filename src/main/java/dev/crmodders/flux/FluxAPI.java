package dev.crmodders.flux;

import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.logging.LogWrapper;
import net.fabricmc.api.ModInitializer;

public class FluxAPI implements ModInitializer {

    @Override
    public void onInitialize() {
        LogWrapper.init();
        LogWrapper.info("Flux Fabric Initialized");
    }

}
