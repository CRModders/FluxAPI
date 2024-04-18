package dev.crmodders.flux.api.v1.modinfo;

import dev.crmodders.flux.annotations.Legacy;

@Legacy
public record EntryPoints(
        String[] main,
        String[] client,
        String[] server,
        String[] preLaunch
) {
}