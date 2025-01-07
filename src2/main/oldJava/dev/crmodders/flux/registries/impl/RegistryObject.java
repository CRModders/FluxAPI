package dev.crmodders.flux.registries.impl;

import dev.crmodders.flux.registries.AccessableRegistry;
import dev.crmodders.flux.registries.DynamicRegistry;
import dev.crmodders.flux.registries.FreezingRegistry;
import dev.crmodders.flux.tags.Identifier;

public class RegistryObject<T> {

    Identifier objectId;
    AccessableRegistry<T> registryAccess;

    public RegistryObject(Identifier id, FreezingRegistry<T> referencedRegistry) {
        objectId = id;
        registryAccess = (AccessableRegistry<T>) referencedRegistry;
    }

    public RegistryObject(Identifier id, DynamicRegistry<T> referencedRegistry) {
        objectId = id;
        registryAccess = (AccessableRegistry<T>) referencedRegistry;
    }

    public T get() {
        if (registryAccess.contains(objectId)) {
            return registryAccess.get(objectId);
        }
        throw new RuntimeException("REGISTRY OBJECT NOT IN REGISTRY: FAILED TO FIND/REGISTER \""+objectId+"\"");
    }

}
