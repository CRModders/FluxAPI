package dev.crmodders.flux.events.system;
/*
 * CODE FROM FABRIC MODIFIED, ORIGINAL LICENSE:
 * the Apache License, Version 2.0
 *
 * Modified by repletsin5
 */
public abstract class Event<T> {
    protected volatile T invoker;

    public final T invoker() {
        return invoker;
    }

    public abstract void register(T listener);
}