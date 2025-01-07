package dev.crmodders.flux.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import de.pottgames.tuningfork.SoundBuffer;
import dev.crmodders.flux.localization.files.LanguageFileVersion1;
import dev.crmodders.flux.tags.ResourceLocation;
import finalforeach.cosmicreach.io.SaveLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxGameAssetLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger("FluxAPI / AssetLoader");

    public static FileHandle locateAsset(String fileName) {
        return locateAsset(ResourceLocation.fromString(fileName));
    }

    public static FileHandle locateAsset(ResourceLocation location) {
        FileHandle classpathLocationFile = Gdx.files.classpath("assets/%s/%s".formatted(location.namespace, location.name));
        if (classpathLocationFile.exists()) {
            LOGGER.info("Loading \u001B[35m\"{}\"\u001B[37m from Java Mod \u001B[32m\"{}\"\u001B[37m", location.name, location.namespace);
            return classpathLocationFile;
        }

        FileHandle modLocationFile = Gdx.files.absolute(SaveLocation.getSaveFolderLocation() + "/mods/assets/" + location.name);
        if (modLocationFile.exists()) {
            LOGGER.info("Loading \u001B[36m\"{}\"\u001B[37m from Mods Folder", location.name);
            return modLocationFile;
        }

        FileHandle vanillaLocationFile = Gdx.files.internal(location.name);
        if (vanillaLocationFile.exists()) {
            LOGGER.info("Loading \u001B[33m\"{}\"\u001B[37m from Cosmic Reach", location.name);
            return vanillaLocationFile;
        }

        LOGGER.error("Cannot find the resource {}", location);
        return null;
    }

    public static final FluxGameAssetLoader LOADER = new FluxGameAssetLoader();

    private final AssetManager assetManager;

    public FluxGameAssetLoader() {
        FileHandleResolver resolver = FluxGameAssetLoader::locateAsset;
        assetManager = new AssetManager(resolver);
        assetManager.setLoader(SoundBuffer.class, new TuningForkLoader(resolver));
        assetManager.setLoader(LanguageFileVersion1.class, new LanguageFileLoader(resolver));
    }

    public <T> void load(String fileName, Class<T> assetClass) {
        assetManager.load(fileName, assetClass);
    }

    public <T> T loadSync(String fileName, Class<T> assetClass) {
        assetManager.load(fileName, assetClass);
        return assetManager.finishLoadingAsset(fileName);
    }

    public <T> T get(String fileName, Class<T> assetClass) {
        if(!assetManager.isLoaded(fileName)) {
            LOGGER.error("Asset not loaded {} ({}) loading now", fileName, assetClass.getSimpleName());
            return loadSync(fileName, assetClass);
        }
        return assetManager.get(fileName);
    }

    public void unload(String fileName) {
        assetManager.unload(fileName);
    }

    public <T> void loadResource(ResourceLocation location, Class<T> assetClass) {
        assetManager.load(location.toString(), assetClass);
    }

    public <T> T loadResourceSync(ResourceLocation location, Class<T> assetClass) {
        assetManager.load(location.toString(), assetClass);
        return assetManager.finishLoadingAsset(location.toString());
    }

    public <T> T getResource(ResourceLocation location, Class<T> assetClass) {
        if(!assetManager.isLoaded(location.toString())) {
            LOGGER.error("Asset not loaded {} ({}) loading now", location, assetClass.getSimpleName());
            return loadResourceSync(location, assetClass);
        }
        return assetManager.get(location.toString(), assetClass);
    }

    public void unloadResource(ResourceLocation location) {
        assetManager.unload(location.toString());
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
