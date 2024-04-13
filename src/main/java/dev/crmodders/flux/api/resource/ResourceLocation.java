package dev.crmodders.flux.api.resource;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameAssetLoader;

/**
 * A Class that stores information about Resources
 * @author Mr-Zombii
 */
public class ResourceLocation extends Identifier {

    public ResourceLocation(String modId, String path) {
        super(modId, path);
    }

    public static ResourceLocation fromString(String id) {
        if (!id.contains(":")) id = "base:"+id;
        String[] splitId = id.split(":");
        return new ResourceLocation(splitId[0], splitId[1]);
    }

    public FileHandle load(boolean forceReload) {
        return GameAssetLoader.loadAsset(toString(), forceReload);
    }

    public FileHandle load() {
        return load(false);
    }

}
