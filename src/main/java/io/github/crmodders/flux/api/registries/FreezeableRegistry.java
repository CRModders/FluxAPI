package io.github.crmodders.flux.api.registries;

public interface FreezeableRegistry<T> {

    void freeze();
    RegistryObject<T> register(Identifier id, T object);

    public static <T> FreezeableRegistry<T> create(Class<T> reference) {
        return new BasicRegistry<T>(reference);
    }

}
