package dev.crmodders.flux.api.resource;

import dev.crmodders.flux.tags.Identifier;

public class ResourceLocation extends Identifier {

    public ResourceLocation(String modId, String path) {
        super(modId, path);
        this.namespace = modId;
        this.name = path;
    }

}
