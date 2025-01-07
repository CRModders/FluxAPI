package dev.crmodders.flux.tags;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.assets.FluxGameAssetLoader;

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

    public FileHandle locate() {
        return FluxGameAssetLoader.locateAsset(this);
    }

}
