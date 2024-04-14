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

        if (assetExists(key))
            resourceObject.handle = GameAssetLoader.loadAsset(key.toString());

        return resourceObject;

    }

    public static boolean assetExists(ResourceLocation location) {
        FileHandle modLocationFile = Gdx.files.absolute(SaveLocation.getSaveFolderLocation() + "/mods/assets/" + location.namespace);
        if (modLocationFile.exists()) return true;
        FileHandle vanillaLocationFile = Gdx.files.internal(location.name);
        if (vanillaLocationFile.exists()) return true;
        FileHandle classpathLocationFile = Gdx.files.classpath("assets/%s/%s".formatted(location.namespace, location.name));
        return classpathLocationFile.exists();
    }
}
