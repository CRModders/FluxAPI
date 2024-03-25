package dev.crmodders.flux;

import org.coolcosmos.cosmicquilt.api.entrypoint.ModInitializer;
import org.quiltmc.loader.api.ModContainer;

public class FluxAPIQuilt implements ModInitializer {

    @Override
    public void onInitialize(ModContainer modContainer) {
        FluxConstants.LOGGER.info("FluxAPI initialized!");
    }

}
