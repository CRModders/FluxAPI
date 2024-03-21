package io.github.crmodders.flux.api.registries;

public class RegistryObject<T> {

    Identifier objectId;
    FreezeableRegistry<T> registryAccess;

    public RegistryObject(Identifier id, FreezeableRegistry<T> referencedRegistry) {
        objectId = id;
        registryAccess = referencedRegistry;
    }

    public T get() {
        if (((BasicRegistry<T>) registryAccess).contains(objectId)) {
            return ((BasicRegistry<T>) registryAccess).get(objectId);
        }
        throw new RuntimeException("REGISTRY OBJECT NOT IN REGISTRY: FAILED TO FIND/REGISTER \""+objectId+"\"");
    }

}
