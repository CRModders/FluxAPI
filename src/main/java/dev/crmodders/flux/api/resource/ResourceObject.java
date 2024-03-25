package dev.crmodders.flux.api.resource;

import com.badlogic.gdx.files.FileHandle;

public class ResourceObject {

    public ResourceKey key;
    public FileHandle handle;

    public ResourceObject(ResourceKey key, FileHandle handle) {
        this.key = key;
        this.handle = handle;
    }

}
