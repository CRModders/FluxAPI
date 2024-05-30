package dev.crmodders.flux.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.Consumer;


public interface CrossLoaderEntrypointUtil {

    static <T> void invoke(String name, Class<T> type, Consumer<? super T> invoker) {
        switch (LoaderDetector.getLoaderKind()) {
            case QUILT -> {
                try {
                    LoaderDetector.class.getClassLoader()
                            .loadClass("org.quiltmc.loader.api.entrypoint.EntrypointUtil")
                            .getMethod("invoke", String.class, Class.class, Consumer.class)
                            .invoke(null, name, type, invoker);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                         ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case FABRIC -> {
                try {
                    Object fabricLoaderInstance = null;

                    fabricLoaderInstance = LoaderDetector.class.getClassLoader()
                            .loadClass("net.fabricmc.loader.api.FabricLoader")
                            .getMethod("getInstance").invoke(null);

                    fabricLoaderInstance.getClass()
                            .getMethod("invokeEntrypoints", String.class, Class.class, Consumer.class)
                            .invoke(fabricLoaderInstance, name, type, invoker);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                         ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case PUZZLE -> {
                try {
                    LoaderDetector.class.getClassLoader()
                            .loadClass("dev.crmodders.puzzle.utils.PuzzleEntrypointUtil")
                            .getMethod("invoke", String.class, Class.class, Consumer.class)
                            .invoke(null, name, type, invoker);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                         ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
