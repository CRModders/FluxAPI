package io.github.crmodders.flux;

import finalforeach.cosmicreach.world.blocks.Block;
import io.github.crmodders.flux.api.blocks.ModBlock;
import io.github.crmodders.flux.api.config.BasicConfig;
import io.github.crmodders.flux.api.generators.data.BlockDataGen;
import io.github.crmodders.flux.api.registries.BuiltInRegistries;
import io.github.crmodders.flux.api.registries.Identifier;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;
import java.util.logging.Logger;

public class FluxAPI implements ModInitializer {
    public static final String MOD_ID = "fluxapi";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        BasicConfig.createIfNotMade("test").addConfigOption("useFluxMenu", false).build();
        LOGGER.info("Hello non Cosmic world!");
    }

    public static BasicConfig getConfig() {
        try {
            return BasicConfig.find("test");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}