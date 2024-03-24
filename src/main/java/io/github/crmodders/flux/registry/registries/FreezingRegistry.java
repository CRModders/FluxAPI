package io.github.crmodders.flux.registry.registries;

import io.github.crmodders.flux.registry.registries.impl.FreezingRegistryImpl;
import io.github.crmodders.flux.registry.registries.impl.RegistryObject;
import io.github.crmodders.flux.tags.Identifier;

public interface FreezingRegistry<T> {

    void freeze();
    boolean isFrozen();
    RegistryObject<T> register(Identifier id, T object);

    public static <T> FreezingRegistry<T> create() {
        return new FreezingRegistryImpl<>();
    }

}
