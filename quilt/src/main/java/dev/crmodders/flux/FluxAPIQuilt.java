package dev.crmodders.flux;

import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.BlockBuilderUtils;
import org.coolcosmos.cosmicquilt.api.entrypoint.ModInitializer;
import org.pmw.tinylog.Logger;
import org.quiltmc.loader.api.ModContainer;

public class FluxAPIQuilt implements ModInitializer {

    @Override
    public void onInitialize(ModContainer mod) {
        Logger.info("Flux Quilt Initialized");
    }

}
