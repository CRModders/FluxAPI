package dev.crmodders.flux.api.resource;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.registry.ExperimentalRegistries;
import dev.crmodders.flux.registry.registries.impl.RegistryObject;
import finalforeach.cosmicreach.GameAssetLoader;

public class AssetLoader {

    public static RegistryObject<ResourceObject> loadAsset(ResourceKey key) {

        ResourceObject resourceObject = new ResourceObject(
                key,
                null
        );

        if (FluxConstants.GameHasLoaded)
            resourceObject.handle = GameAssetLoader.loadAsset(key.toString());

        return ExperimentalRegistries.ResourceRegistry.register(
                key,
                resourceObject
        );

    }
}
