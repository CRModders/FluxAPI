package dev.crmodders.flux.util;

public interface LoaderDetector {

    static boolean isOnFabricBasedLoader() {
        try {
            LoaderDetector.class.getClassLoader().loadClass("net.fabricmc.loader.impl.FabricLoaderImpl");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    static boolean isOnQuiltBasedLoader() {
        try {
            LoaderDetector.class.getClassLoader().loadClass("org.quiltmc.loader.impl.QuiltLoaderImpl");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    static boolean isOnPuzzleLoader() {
        try {
            LoaderDetector.class.getClassLoader().loadClass("dev.crmodders.puzzle.core.launch.Piece");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    static boolean isOnNonFabricOrQuiltBasedLoader() {
        return !(isOnFabricBasedLoader() || isOnQuiltBasedLoader());
    }

    static KnownLoader getLoaderKind() {
        if (isOnFabricBasedLoader()) return KnownLoader.FABRIC;
        if (isOnQuiltBasedLoader()) return KnownLoader.QUILT;
        if (isOnPuzzleLoader()) return KnownLoader.PUZZLE;
        return KnownLoader.UNKNOWN;
    }

    enum KnownLoader {
        FABRIC,
        QUILT,
        PUZZLE,
        UNKNOWN
    }

}
