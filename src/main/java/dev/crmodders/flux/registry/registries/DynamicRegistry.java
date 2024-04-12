package dev.crmodders.flux.registry.registries;

import dev.crmodders.flux.registry.registries.impl.DynamicRegistryImpl;
import dev.crmodders.flux.registry.registries.impl.RegistryObject;
import dev.crmodders.flux.tags.Identifier;

public interface DynamicRegistry<T> {

    RegistryObject<T> register(Identifier id, T object);

    AccessableRegistry<T> access() throws NotAccessibleException;

    static <T> DynamicRegistry<T> create() {
        return new DynamicRegistryImpl<>();
    }

}
