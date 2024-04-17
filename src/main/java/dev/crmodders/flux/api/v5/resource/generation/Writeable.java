package dev.crmodders.flux.api.v5.resource.generation;

import dev.crmodders.flux.tags.Identifier;
import org.hjson.JsonObject;

/**
 * Defines an abstract class that represents an object that can be written to a file.
 */
public abstract class Writeable {

    public boolean written;

    public Writeable() {
        this.written = false;
    }

    /**
     * Serializes the object to a JSON object.
     *
     * @return {JsonObject} The serialized object as a JSON object.
     */
    public JsonObject serialize() {
        return new JsonObject();
    }

}
