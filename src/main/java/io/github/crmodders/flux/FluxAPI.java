package io.github.crmodders.flux;

import io.github.crmodders.flux.config.BasicConfig;
import net.fabricmc.api.ModInitializer;
import java.util.logging.Logger;

public class FluxAPI implements ModInitializer {
    public static final String MOD_ID = "fluxapi";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        BasicConfig.createIfNotMade("test").addConfigOption("test", "test").build();
        LOGGER.info("Hello non Cosmic world!");
    }



}