package dev.crmodders.flux.api.v6.resource;

import com.badlogic.gdx.files.FileHandle;

import javax.annotation.Nullable;

/**
 * A resource object that holds its location and file handle
 */
public class ResourceObject {

    public ResourceLocation key;
    public FileHandle handle;

    /**
     * @param key the location for the resource
     * @param handle the handle that may be null depending on where your mod is loaded or where this method is used during init.
     */
    public ResourceObject(ResourceLocation key, @Nullable FileHandle handle) {
        this.key = key;
        this.handle = handle;
    }

}
