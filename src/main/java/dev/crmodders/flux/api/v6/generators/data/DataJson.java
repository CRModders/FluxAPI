package dev.crmodders.flux.api.v6.generators.data;

import org.hjson.JsonObject;

/**
 * The Base interface for any item that needs to be converted to json.
 */
public interface DataJson {

    JsonObject toJson();

}
