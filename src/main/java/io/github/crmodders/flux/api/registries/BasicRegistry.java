package io.github.crmodders.flux.api.registries;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class BasicRegistry<T> implements FreezeableRegistry<T> {

    ImmutableMap.Builder<String, T> OBJECTS_BUILDER = new ImmutableMap.Builder<>();
    public ImmutableMap<String, T> OBJECTS;

    protected BasicRegistry(Class<T> reference) {

    }

    protected T get(Identifier id) {
        return OBJECTS.get(id.toString());
    }

    protected boolean contains(Identifier id) {
        return OBJECTS.containsKey(id.toString());
    }

    @Override
    public void freeze() {
        OBJECTS = OBJECTS_BUILDER.build();
    }

    @Override
    public RegistryObject<T> register(Identifier id, T object) {
//        if (!OBJECTS_BUILDER.(id.toString())) {
            OBJECTS_BUILDER.put(id.toString(), object);
            return new RegistryObject<>(id, this);
//        }
//        throw new RuntimeException("DUPLICATED REGISTRY OBJECT \""+id+"\"");
    }
}
