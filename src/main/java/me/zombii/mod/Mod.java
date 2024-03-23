package me.zombii.mod;

import io.github.crmodders.flux.api.config.BasicConfig;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;
import java.util.logging.Logger;

public class Mod implements ModInitializer {
    public static final String MOD_ID = "fluxapi";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        BasicConfig.createIfNotMade("test")
                .addConfigOption("useCustomMenu", false).build();
    }

    public static BasicConfig getConfig() {
        try {
            return BasicConfig.find("test");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}