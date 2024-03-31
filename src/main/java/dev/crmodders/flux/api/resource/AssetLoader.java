package dev.crmodders.flux.api.resource;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.impl.RegistryObject;
import finalforeach.cosmicreach.GameAssetLoader;

public class AssetLoader {

    public static RegistryObject<ResourceObject> loadAsset(ResourceLocation key) {

        ResourceObject resourceObject = new ResourceObject(
                key,
                null
        );

        if (FluxConstants.GameHasLoaded)
            resourceObject.handle = GameAssetLoader.loadAsset(key.toString());

        return FluxRegistries.GAME_RESOURCES.register(
                key,
                resourceObject
        );

    }

    public static ResourceObject unsafeLoadAsset(ResourceLocation key) {

        ResourceObject resourceObject = new ResourceObject(
                key,
                null
        );

        if (FluxConstants.GameHasLoaded)
            resourceObject.handle = GameAssetLoader.loadAsset(key.toString());

        return resourceObject;

    }
}
