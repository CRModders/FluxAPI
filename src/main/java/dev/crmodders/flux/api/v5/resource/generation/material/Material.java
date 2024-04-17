package dev.crmodders.flux.api.v5.resource.generation.material;

import dev.crmodders.flux.api.v5.resource.ResourceLocation;
import org.hjson.JsonObject;

public class Material {
    public static int materialCount = 0;

    /**
     * The unique id for this material.
     */
    String id;

    /**
     * The file name for this material.
     */
    ResourceLocation fileName;

    /**
     * Initializes a new Material.
     *
     * @param {Texture} texture The texture for this material.
     */
    public Material(ResourceLocation fileName) {
        this.id = "m_" + materialCount++;
        this.fileName = fileName;
    }

    /**
     * Serializes the Material into a JSON object.
     *
     * @return {Object} The serialized Material as a JSON object.
     */
    public JsonObject serialize() {
        return new JsonObject().set("fileName", fileName.toString());
    }
}
