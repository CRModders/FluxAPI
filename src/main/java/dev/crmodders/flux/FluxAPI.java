package dev.crmodders.flux;

import net.fabricmc.api.ModInitializer;
import org.pmw.tinylog.Logger;

public class FluxAPI implements ModInitializer {

    @Override
    public void onInitialize() {
        Logger.info("Flux Fabric Initialized");
    }

}
