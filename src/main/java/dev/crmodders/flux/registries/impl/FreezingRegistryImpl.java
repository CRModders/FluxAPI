package dev.crmodders.flux.registries.impl;

import dev.crmodders.flux.registries.AccessableRegistry;
import dev.crmodders.flux.registries.FreezingRegistry;
import dev.crmodders.flux.registries.NotAccessibleException;
import dev.crmodders.flux.tags.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class FreezingRegistryImpl<T> implements AccessableRegistry<T>, FreezingRegistry<T> {

    private boolean isFrozen;
    private final Map<Identifier, T> objects;

    public FreezingRegistryImpl() {
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
    public void freeze() {
        isFrozen = true;
    }

    @Override
    public boolean isFrozen() {
        return isFrozen;
    }

    @Override
    public RegistryObject<T> register(Identifier id, T object) {
        if (isFrozen) throw new RuntimeException("CANNOT REGISTER AFTER REGISTRY IS FROZEN");
        objects.put(id, object);

        return new RegistryObject<>(id, this);
    }

    @Override
    public AccessableRegistry<T> access() throws NotAccessibleException {
        return this;
    }

}
