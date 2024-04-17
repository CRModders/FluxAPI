package dev.crmodders.flux.api.v1.modinfo;

public record EntryPoints(
        String[] main,
        String[] client,
        String[] server,
        String[] preLaunch
) {
}