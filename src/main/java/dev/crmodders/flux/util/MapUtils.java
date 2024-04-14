package dev.crmodders.flux.util;

import dev.crmodders.flux.api.suppliers.ReturnableInputSupplier;
import dev.crmodders.flux.logging.LogWrapper;

import java.util.HashMap;
import java.util.Map;

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

    public static <A, B>  HashMap<A, B> manipulateMapValue(HashMap<A, B> map, A key, ReturnableInputSupplier<B, B> valueManipulator) {
        map.put(key, valueManipulator.get(map.get(key)));
        return map;
    }
}
