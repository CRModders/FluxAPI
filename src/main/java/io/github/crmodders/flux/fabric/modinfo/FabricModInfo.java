package io.github.crmodders.flux.fabric.modinfo;

import io.github.crmodders.flux.fabric.modinfo.json.Dependency;
import io.github.crmodders.flux.fabric.modinfo.json.FabricModJson;

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