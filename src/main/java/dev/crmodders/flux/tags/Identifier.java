package dev.crmodders.flux.tags;

import dev.crmodders.flux.annotations.Stable;
import finalforeach.cosmicreach.io.CosmicReachBinaryDeserializer;
import finalforeach.cosmicreach.io.CosmicReachBinarySerializer;
import finalforeach.cosmicreach.io.ICosmicReachBinarySerializable;

import java.util.Objects;

/**
 * Stores information about Registered Objects in FluxAPI
 * such as {@link dev.crmodders.flux.api.v5.resource.ResourceLocation}
 * contains a namespace and name
 * namespaces are usually the modid
 * @author Mr-Zombii
 */
@Stable
public class Identifier implements ICosmicReachBinarySerializable {

    public String namespace;
    public String name;

    public Identifier(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    @Override
    public String toString() {
        return namespace + ":" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(namespace, that.namespace) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, name);
    }

    public static Identifier fromString(String id) {
        if (!id.contains(":")) id = "base:"+id;
        String[] splitId = id.split(":");
        return new Identifier(splitId[0], splitId[1]);
    }

    public void readFromString(String id) {
        if (!id.contains(":")) id = "base:"+id;
        String[] splitId = id.split(":");
        this.namespace = splitId[0];
        this.name = splitId[1];
    }


    @Override
    public void read(CosmicReachBinaryDeserializer deserializer) {
        readFromString(deserializer.readString("stringId"));
    }

    @Override
    public void write(CosmicReachBinarySerializer serializer) {
        serializer.writeString("stringId", namespace + ":" + name);
    }
}
