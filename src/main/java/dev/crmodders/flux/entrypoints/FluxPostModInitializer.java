package dev.crmodders.flux.entrypoints;

import dev.crmodders.flux.util.CrossLoaderEntrypointUtil;

public interface FluxPostModInitializer {

    String ENTRYPOINT_KEY = "fluxPostInit";

    void onPostInit();

    static void invokeEntrypoint() {
        CrossLoaderEntrypointUtil.invoke(ENTRYPOINT_KEY, FluxPostModInitializer.class, FluxPostModInitializer::onPostInit);
    }

}
