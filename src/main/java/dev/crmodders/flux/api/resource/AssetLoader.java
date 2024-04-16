package dev.crmodders.flux.api.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.impl.RegistryObject;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.io.SaveLocation;
import org.pmw.tinylog.Logger;

import static finalforeach.cosmicreach.GameAssetLoader.ALL_ASSETS;

public class AssetLoader {

    /**
     * A method to load assets that outputs a RegistryObject that when called will load the object from the registry.
     * @param key the resource location.
     *
     * @return the registry object to possibly make the registry safer from null ptrs.
     */
    public static RegistryObject<ResourceObject> loadAsset(ResourceLocation key) {

        ResourceObject resourceObject = new ResourceObject(
                key,
                null
        );

        if (FluxConstants.GameHasLoaded)
            resourceObject.handle = unsafeLoadAsset(key).handle;

        return FluxRegistries.GAME_RESOURCES.register(
                key,
                resourceObject
        );

    }

    /**
     * A method to load the assets mentioned by the key param that only works if the asset exists and the handle may be null in certain situations.
     * @param key the loaded resource.
     *
     * @return the resource object that holds the key and FileHandle.
     */
    public static ResourceObject unsafeLoadAsset(ResourceLocation key) {

        ResourceObject resourceObject = new ResourceObject(
                key,
                null
        );

        if (assetExists(key))
            resourceObject.handle = key.load();

        return resourceObject;

    }

    /**
     * A method to check if a resource exists.
     * @param location the resource location to check.
     *
     * @return the condition saying if the asset exists or not.
     */
    public static boolean assetExists(ResourceLocation location) {
        FileHandle modLocationFile = Gdx.files.absolute(SaveLocation.getSaveFolderLocation() + "/mods/assets/" + location.namespace);
        if (modLocationFile.exists()) return true;
        FileHandle vanillaLocationFile = Gdx.files.internal(location.name);
        if (vanillaLocationFile.exists()) return true;
        FileHandle classpathLocationFile = Gdx.files.classpath("assets/%s/%s".formatted(location.namespace, location.name));
        return classpathLocationFile.exists();
    }
}
