package dev.crmodders.flux.api.resource;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.tags.Identifier;

import java.util.Objects;

public class ResourceKey extends Identifier {

    public ResourceKey(String modId, String path) {
        super(modId, path);
        this.namespace = modId;
        this.name = path;
    }

}
