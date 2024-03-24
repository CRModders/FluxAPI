package io.github.crmodders.flux.util;

import java.lang.reflect.Field;

public class PrivUtils {
    public static Object getPrivField(Class<?> clazz, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(clazz);
    }

    public static void setPrivField(Class<?> clazz, String fieldName, Object data) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(clazz, data);
    }
}
