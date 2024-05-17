package dev.crmodders.flux.registry.registries;

import dev.crmodders.flux.tags.Identifier;

import java.util.Iterator;

public interface AccessableRegistry<T> extends Iterable<T> {

    T get(Identifier identifier);
    boolean contains(Identifier identifier);
    Identifier[] getRegisteredNames();

    default Iterator<T> iterator() {
        return new RegistryIterator<>(this);
    }

}
