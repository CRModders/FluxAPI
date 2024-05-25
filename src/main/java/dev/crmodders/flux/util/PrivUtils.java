package dev.crmodders.flux.util;

import java.lang.reflect.Field;

public class PrivUtils {

    @SuppressWarnings("unchecked")
    public static <T> T getPrivField(Class<?> clazz, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        return (T) f.get(null);
    }

    public static void setPrivField(Class<?> clazz, String fieldName, Object data) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(null, data);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getPrivField(Object instance, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = instance.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return (T) f.get(instance);
    }

    public static void setPrivField(Object instance, String fieldName, Object data) throws NoSuchFieldException, IllegalAccessException {
        Field f = instance.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(instance, data);
    }

}
