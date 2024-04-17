package dev.crmodders.flux.api.v1.modinfo;

import dev.crmodders.flux.api.v1.modinfo.json.Dependency;

public record FabricModInfo(
        int SchemaVersion,
        String modID,
        String version,
        String name,
        String description,
        String[] authors,
        String license,
        String enviornment,
        EntryPoints entryPoints,
        String[] mixins,
        Dependency[] dependencies
) { }