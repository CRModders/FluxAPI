package dev.crmodders.flux.util;

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

}
