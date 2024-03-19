package io.github.crmodders.flux;

import com.badlogic.gdx.graphics.Pixmap;
import finalforeach.cosmicreach.rendering.shaders.ChunkShader;
import io.github.crmodders.flux.config.BasicConfig;
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