package dev.crmodders.flux.api.v5.suppliers;

@FunctionalInterface
public interface ReturnableInputSupplier<I, O> {

    O get(I item);

}
