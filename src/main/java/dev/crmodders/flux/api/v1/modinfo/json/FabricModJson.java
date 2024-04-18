package dev.crmodders.flux.api.v1.modinfo.json;

import dev.crmodders.flux.annotations.LegacyInternal;
import org.hjson.JsonObject;

@LegacyInternal
public class FabricModJson {

    public int schemaVersion;
    public String id;
    public String version;
    public String name;
    public String description;
    public String[] authors;
    public String license;
    public String enviornment;
    public EntrypointsJson entrypoints;
    public String[] mixins;
    public JsonObject depends;

}