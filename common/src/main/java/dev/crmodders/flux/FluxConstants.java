package dev.crmodders.flux;

import dev.crmodders.flux.api.block.TestBlock;
import dev.crmodders.flux.registry.StableRegistries;
import dev.crmodders.flux.tags.Identifier;
import org.pmw.tinylog.Logger;

public class FluxConstants {
    public static boolean GameHasLoaded;

    public static final String MOD_ID = "fluxapi";
    public static final String ASSET_KEY = "¬¬$^$^¬¬";

    static {
        StableRegistries.BLOCKS.register(
                new Identifier(MOD_ID, "test"),
                new TestBlock()
        );
    }

}
