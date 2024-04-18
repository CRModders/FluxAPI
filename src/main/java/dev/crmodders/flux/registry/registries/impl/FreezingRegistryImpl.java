package dev.crmodders.flux.registry.registries.impl;

import dev.crmodders.flux.events.system.Event;
import dev.crmodders.flux.events.system.EventFactory;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.registry.registries.FreezingRegistry;
import dev.crmodders.flux.registry.registries.ListenerActiveRegistry;
import dev.crmodders.flux.registry.registries.NotAccessibleException;
import dev.crmodders.flux.tags.Identifier;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class FreezingRegistryImpl<T> implements AccessableRegistry<T>, FreezingRegistry<T>, ListenerActiveRegistry<T> {

    private boolean isFrozen;
    private final Map<Identifier, T> objects;
    private final Map<Identifier, Event<RegistryObjectListener<T>>> listeners;

    public FreezingRegistryImpl() {
        objects = new LinkedHashMap<>();
        listeners = new LinkedHashMap<>();
    }

    @Override
    public T get(Identifier identifier) {
        return objects.get(identifier);
    }

    @Override
    public Event<RegistryObjectListener<T>> getListenersOfObject(Identifier identifier) {
        return listeners.get(identifier);
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
        if (!listeners.containsKey(id)) listeners.put(id, createListenerEvents(id));
        objects.put(id, object);

        return new RegistryObject<>(id, this);
    }

    @Override
    public void registerListener(Identifier id, RegistryObjectListener<T> listener) {
        if (isFrozen) throw new RuntimeException("CANNOT REGISTER AFTER REGISTRY IS FROZEN");
        if (!listeners.containsKey(id)) listeners.put(id, createListenerEvents(id));

        listeners.get(id).register(listener);
    }

    @Override
    public AccessableRegistry<T> access() throws NotAccessibleException {
        return this;
    }

    @Override
    public ListenerActiveRegistry<T> asListenerActive() throws NotAccessibleException {
        return this;
    }

    private Event<RegistryObjectListener<T>> createListenerEvents(Identifier id) {
        return EventFactory.createArrayBacked(
                RegistryObjectListener.class,
                callbacks -> (o) -> {
                    Arrays.stream(callbacks)
                            .toList()
                            .forEach(
                                    listener -> listener.onInternalRegister(get(id))
                            );
                }
        );

    }
}
