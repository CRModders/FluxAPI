package dev.crmodders.flux.api.v6.suppliers;

@FunctionalInterface
public interface ReturnableSupplier<T> {

    T get();

}
