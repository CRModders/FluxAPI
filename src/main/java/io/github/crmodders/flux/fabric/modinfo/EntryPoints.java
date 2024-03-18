package io.github.crmodders.flux.fabric.modinfo;

public record EntryPoints(
        String[] main,
        String[] client,
        String[] server,
        String[] preLaunch
) {
}