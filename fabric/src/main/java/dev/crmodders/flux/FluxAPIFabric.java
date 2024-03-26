package dev.crmodders.flux;

import net.fabricmc.api.ModInitializer;
import org.pmw.tinylog.Logger;

public class FluxAPIFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Logger.info("Flux Fabric Initialized");
    }

}
