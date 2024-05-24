package dev.crmodders.flux.registries;

import dev.crmodders.flux.registries.impl.FreezingRegistryImpl;
import dev.crmodders.flux.registries.impl.RegistryObject;
import dev.crmodders.flux.tags.Identifier;

public interface FreezingRegistry<T> {

    void freeze();
    boolean isFrozen();
    RegistryObject<T> register(Identifier id, T object);

    AccessableRegistry<T> access() throws NotAccessibleException;

    static <T> FreezingRegistry<T> create() {
        return new FreezingRegistryImpl<>();
    }


}
