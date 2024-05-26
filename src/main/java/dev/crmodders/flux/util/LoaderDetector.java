package dev.crmodders.flux.util;

import java.util.function.Consumer;

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

    static boolean isOnNonFabricOrQuiltBasedLoader() {
        return !(isOnFabricBasedLoader() || isOnQuiltBasedLoader());
    }

    static KnownLoader getLoaderKind() {
        if (isOnFabricBasedLoader()) return KnownLoader.FABRIC;
        if (isOnQuiltBasedLoader()) return KnownLoader.QUILT;
        return KnownLoader.UNKNOWN;
    }

    enum KnownLoader {
        FABRIC,
        QUILT,
        UNKNOWN
    }

}
