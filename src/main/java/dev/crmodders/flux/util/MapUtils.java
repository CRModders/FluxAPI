package dev.crmodders.flux.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MapUtils {

    public static <A, B> Map<A, B> initMap(Map<A, B> map, A[] keys, B initializer) {
        if (map == null) map = new HashMap<>();

        for (A key : keys) map.put(key, initializer);
        return map;
    }

    public static <A, B> Map<A, B> initMap(A[] keys, B initializer) {
        Map<A, B> map = new HashMap<>();

        for (A key : keys) map.put(key, initializer);
        return map;
    }

    public static <A, B>  Map<A, B> initMap(Map<A, B> map, A[] keys, B[] initializers) {
        if (map == null) map = new HashMap<>();

        for (int i = 0; i < keys.length; i++) {
            A key = keys[i];
            B initializer = initializers[i];
            map.put(key, initializer);
        }

        return map;
    }

    public static <A, B> Map<A, B> initMap(A[] keys, B[] initializers) {
        Map<A, B> map = new HashMap<>();

        for (int i = 0; i < keys.length; i++) {
            A key = keys[i];
            B initializer = initializers[i];
            map.put(key, initializer);
        }

        return map;
    }

    public static <A, B>  HashMap<A, B> manipulateMapValue(HashMap<A, B> map, A key, Function<B, B> valueManipulator) {
        map.put(key, valueManipulator.apply(map.get(key)));
        return map;
    }
}
