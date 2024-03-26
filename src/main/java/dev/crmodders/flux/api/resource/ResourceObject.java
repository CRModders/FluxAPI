package dev.crmodders.flux.api.resource;

import com.badlogic.gdx.files.FileHandle;

public class ResourceObject {

    public ResourceLocation key;
    public FileHandle handle;

    public ResourceObject(ResourceLocation key, FileHandle handle) {
        this.key = key;
        this.handle = handle;
    }

}
