package dev.crmodders.flux.util;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;


public class EntrypointUtil {

    public static <T> void invoke(String name, Class<T> type, Consumer<? super T> invoker) {
        switch (LoaderKind.getLoaderKind()) {
            case QUILT -> {
                try {
                    Class.forName("org.quiltmc.loader.api.entrypoint.EntrypointUtil")
                            .getMethod("invoke", String.class, Class.class, Consumer.class)
                            .invoke(null, name, type, invoker);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                         ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case FABRIC -> {
                try {
                    Object fabricLoaderInstance = Class.forName("net.fabricmc.loader.api.FabricLoader")
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
                    Class.forName("dev.crmodders.puzzle.utils.PuzzleEntrypointUtil")
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
