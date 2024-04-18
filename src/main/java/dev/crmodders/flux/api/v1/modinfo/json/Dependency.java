package dev.crmodders.flux.api.v1.modinfo.json;

import dev.crmodders.flux.annotations.LegacyInternal;

@LegacyInternal
public record Dependency(
        String modID,
        String version
) {
}