package io.github.crmodders.flux.api.config.modinfo;

import io.github.crmodders.flux.api.config.modinfo.json.Dependency;

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