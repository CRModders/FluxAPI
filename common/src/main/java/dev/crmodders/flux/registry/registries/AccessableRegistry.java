package dev.crmodders.flux.registry.registries;

import dev.crmodders.flux.tags.Identifier;

public interface AccessableRegistry<T> {

    T get(Identifier identifier);
    boolean contains(Identifier identifier);
    Identifier[] getRegisteredNames();

}
