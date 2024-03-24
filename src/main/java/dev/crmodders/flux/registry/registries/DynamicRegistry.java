package io.github.crmodders.flux.registry.registries;

import io.github.crmodders.flux.registry.registries.impl.DynamicRegistryImpl;
import io.github.crmodders.flux.registry.registries.impl.RegistryObject;
import io.github.crmodders.flux.tags.Identifier;

public interface DynamicRegistry<T> {

    RegistryObject<T> register(Identifier id, T object);

    public static <T> DynamicRegistry<T> create() {
        return new DynamicRegistryImpl<>();
    }

}
