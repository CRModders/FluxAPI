package io.github.crmodders.flux.api.suppliers;

@FunctionalInterface
public interface ReturnableSupplier<T> {

    T get();

}
