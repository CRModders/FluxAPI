package dev.crmodders.flux.api.events.system;

import java.lang.reflect.Array;
import java.util.Arrays;

/*
 * CODE FROM FABRIC MODIFIED, ORIGINAL LICENSE:
 * the Apache License, Version 2.0
 *
 * Modified by repletsin5
 */
class EventData<T>  {
    T[] listeners;

    @SuppressWarnings("unchecked")
    EventData( Class<?> listenerClass) {
        this.listeners = (T[]) Array.newInstance(listenerClass, 0);
    }

    void addListener(T listener) {
        int oldLength = listeners.length;
        listeners = Arrays.copyOf(listeners, oldLength + 1);
        listeners[oldLength] = listener;
    }
}