package dev.crmodders.flux.impl.input.client;

import com.badlogic.gdx.Gdx;
import dev.crmodders.cosmicquilt.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.loader.api.ModContainer;

public final class FluxApiInputClientEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient(final ModContainer modContainer) {
        Gdx.input = new FluxInput(Gdx.input);
    }
}
