package dev.crmodders.flux.api.v5.suppliers;

@FunctionalInterface
public interface ReturnableSupplier<T> {

    T get();

}
