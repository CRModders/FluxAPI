package io.github.crmodders.flux.api.config.modinfo;

public record EntryPoints(
        String[] main,
        String[] client,
        String[] server,
        String[] preLaunch
) {
}