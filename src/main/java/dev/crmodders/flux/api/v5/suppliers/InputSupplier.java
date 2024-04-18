package dev.crmodders.flux.api.v5.suppliers;

@FunctionalInterface
public interface InputSupplier<T> {

    void get(T item);

}
