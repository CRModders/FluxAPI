package dev.crmodders.flux.registry.registries.impl;

import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.registry.registries.FreezingRegistry;
import dev.crmodders.flux.tags.Identifier;

import java.util.HashMap;

public class FreezingRegistryImpl<T> implements AccessableRegistry<T>, FreezingRegistry<T> {

    private boolean isFrozen;
    private HashMap<Identifier, T> objects;

    public FreezingRegistryImpl() {
        objects = new HashMap<>();
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
}
