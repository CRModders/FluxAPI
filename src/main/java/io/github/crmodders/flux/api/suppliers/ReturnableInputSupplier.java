package io.github.crmodders.flux.api.suppliers;

@FunctionalInterface
public interface ReturnableInputSupplier<I, O> {

    O get(I item);

}
