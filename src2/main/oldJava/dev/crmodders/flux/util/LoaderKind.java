package dev.crmodders.flux.util;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModOrigin;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public enum LoaderKind {

    FABRIC,
    QUILT,
    PUZZLE,
    UNKNOWN;

    public static boolean isOnFabricBasedLoader() {
        try {
            Class.forName("net.fabricmc.loader.impl.FabricLoaderImpl");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public static boolean isOnQuiltBasedLoader() {
        try {
            Class.forName("org.quiltmc.loader.impl.QuiltLoaderImpl");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public static boolean isOnPuzzleLoader() {
        try {
            Class.forName("dev.crmodders.puzzle.core.launch.Piece");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public static LoaderKind getLoaderKind() {
        if (isOnPuzzleLoader()) return PUZZLE;
        if (isOnQuiltBasedLoader()) return QUILT;
        if (isOnFabricBasedLoader()) return FABRIC;
        return UNKNOWN;
    }

    public static Stream<Path> getModLocations() {
        return switch (getLoaderKind()) {
            case FABRIC -> FabricLoader.getInstance()
                    .getAllMods()
                    .stream()
                    .map(it -> it.getOrigin())
                    .filter(it -> it.getKind() == ModOrigin.Kind.PATH)
                    .map(ModOrigin::getPaths)
                    .flatMap(List::stream);
            case QUILT -> QuiltLoader.getAllMods()
                    .stream()
                    .filter(mod -> mod.getSourceType() != ModContainer.BasicSourceType.BUILTIN)
                    .map(it -> it.getSourcePaths())
                    .flatMap(List::stream)
                    .flatMap(List::stream);
            default -> Stream.empty();
        };
    }
}
