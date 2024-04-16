package dev.crmodders.flux.tags;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import dev.crmodders.flux.annotations.Stable;

import java.util.Objects;

/**
 * Stores information about Registered Objects in FluxAPI
 * such as {@link dev.crmodders.flux.api.resource.ResourceLocation}
 * contains a namespace and name
 * namespaces are usually the modid
 * @author Mr-Zombii
 */
@Stable
public class Identifier implements Json.Serializable {

    public String namespace;
    public String name;

    public Identifier(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    public Identifier() {}

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

    public void internalFromString(String id) {
        if (!id.contains(":")) id = "base:"+id;
        String[] splitId = id.split(":");
        namespace = splitId[0];
        name = splitId[1];
    }

    @Override
    public void write(Json json) {
        throw new RuntimeException("Cannot Serialize Identifier: Not Implemented");
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        internalFromString(jsonValue.asString());
    }
}
