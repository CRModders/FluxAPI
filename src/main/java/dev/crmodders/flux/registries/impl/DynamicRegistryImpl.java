package dev.crmodders.flux.registries.impl;

import dev.crmodders.flux.registries.AccessableRegistry;
import dev.crmodders.flux.registries.DynamicRegistry;
import dev.crmodders.flux.registries.NotAccessibleException;
import dev.crmodders.flux.tags.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class DynamicRegistryImpl<T> implements AccessableRegistry<T>, DynamicRegistry<T> {

    private Map<Identifier, T> objects;

    public DynamicRegistryImpl() {
        objects = new LinkedHashMap<>();
    }

    @Override
    public T get(Identifier identifier) {
        return objects.get(identifier);
    }

    @Override
    public boolean contains(Identifier identifier) {
        return objects.containsKey(identifier);
    }

    @Override
    public Identifier[] getRegisteredNames() {
        return objects.keySet().toArray(new Identifier[0]);
    }

    @Override
    public RegistryObject<T> register(Identifier id, T object) {
        objects.put(id, object);
        return new RegistryObject<>(id, this);
    }

    @Override
    public AccessableRegistry<T> access() throws NotAccessibleException {
        return this;
    }
}
