package io.github.crmodders.flux.registry.registries;

import io.github.crmodders.flux.tags.Identifier;

public interface AccessableRegistry<T> {

    T get(Identifier identifier);
    boolean contains(Identifier identifier);
    Identifier[] getRegisteredNames();

}
