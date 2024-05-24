package dev.crmodders.flux.registries;

import dev.crmodders.flux.tags.Identifier;

import java.util.Arrays;
import java.util.Iterator;

public class RegistryIterator<T> implements Iterator<T> {

    private final AccessableRegistry<T> registry;
    private final Iterator<Identifier> it;

    public RegistryIterator(AccessableRegistry<T> registry) {
        this.registry = registry;
        this.it = Arrays.stream(registry.getRegisteredNames()).iterator();
    }


    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public T next() {
        return registry.get(it.next());
    }
}
