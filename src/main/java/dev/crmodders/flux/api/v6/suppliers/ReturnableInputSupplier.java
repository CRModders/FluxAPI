package dev.crmodders.flux.api.v6.suppliers;

@FunctionalInterface
public interface ReturnableInputSupplier<I, O> {

    O get(I item);

}
