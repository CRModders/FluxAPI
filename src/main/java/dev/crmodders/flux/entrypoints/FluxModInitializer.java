package dev.crmodders.flux.entrypoints;

import dev.crmodders.flux.util.CrossLoaderEntrypointUtil;

public interface FluxModInitializer {

    String ENTRYPOINT_KEY = "fluxInit";

    void onInit();

    static void invokeEntrypoint() {
        CrossLoaderEntrypointUtil.invoke(ENTRYPOINT_KEY, FluxModInitializer.class, FluxModInitializer::onInit);
    }

}
