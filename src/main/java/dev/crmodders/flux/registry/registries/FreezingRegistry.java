package dev.crmodders.flux.registry.registries;

import dev.crmodders.flux.registry.registries.impl.FreezingRegistryImpl;
import dev.crmodders.flux.registry.registries.impl.RegistryObject;
import dev.crmodders.flux.tags.Identifier;

public interface FreezingRegistry<T> {

    void freeze();
    boolean isFrozen();
    RegistryObject<T> register(Identifier id, T object);

    AccessableRegistry<T> access() throws NotAccessibleException;

    public static <T> FreezingRegistry<T> create() {
        return new FreezingRegistryImpl<>();
    }

}
