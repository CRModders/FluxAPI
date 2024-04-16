package dev.crmodders.flux.tags;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import dev.crmodders.flux.annotations.Stable;
import dev.crmodders.flux.logging.LogWrapper;
import finalforeach.cosmicreach.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.blockevents.actions.BlockActionRunTrigger;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJson;

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

    private String serializedName;
    public String namespace;
    public String name;

    public Identifier() {}

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

    public void internalFromString(String id) {
        if (!id.contains(":")) id = "base:"+id;
        String[] splitId = id.split(":");
        namespace = splitId[0];
        name = splitId[1];
    }

    @Override
    public void write(Json json) {
        json.writeValue(serializedName, toString());
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        internalFromString(jsonValue.asString());
        serializedName = jsonValue.name();
    }
}
