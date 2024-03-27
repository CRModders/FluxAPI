package dev.crmodders.flux;

import dev.crmodders.flux.api.events.GameEvents;
import net.fabricmc.api.ModInitializer;
import org.pmw.tinylog.Logger;

public class FluxAPI implements ModInitializer {

    @Override
    public void onInitialize() {
        Logger.info("Flux Fabric Initialized");
    }

}
