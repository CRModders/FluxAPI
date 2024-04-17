package dev.crmodders.flux.api.v1.modinfo.json;

import org.hjson.JsonObject;

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