package dev.crmodders.flux.registry.registries;

import dev.crmodders.flux.api.v6.events.system.Event;
import dev.crmodders.flux.tags.Identifier;

public interface ListenerActiveRegistry<T> {


    void registerListener(Identifier id, RegistryObjectListener<T> listener);
    Event<RegistryObjectListener<T>> getListenersOfObject(Identifier identifier);

    @FunctionalInterface
    interface RegistryObjectListener<T> {
        void onInternalRegister(T object);
    }
}
