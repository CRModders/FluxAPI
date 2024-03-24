package io.github.crmodders.flux.api.suppliers;

@FunctionalInterface
public interface InputSupplier<T> {

    void get(T item);

}
