package dev.crmodders.flux.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    public static Method getMethod(Class<?> clazz, String methodName) {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }
}
