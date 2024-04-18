package dev.crmodders.flux.api.v6.suppliers;

@FunctionalInterface
public interface InputSupplier<T> {

    void get(T item);

}
