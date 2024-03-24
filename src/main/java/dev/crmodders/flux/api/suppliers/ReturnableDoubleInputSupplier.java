package dev.crmodders.flux.api.suppliers;

@FunctionalInterface
public interface ReturnableDoubleInputSupplier<I, I2, O> {

    O get(I item, I2 item2);

}
