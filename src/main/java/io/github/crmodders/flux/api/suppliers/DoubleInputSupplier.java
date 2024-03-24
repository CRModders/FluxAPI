package io.github.crmodders.flux.api.suppliers;

@FunctionalInterface
public interface DoubleInputSupplier<I, I2> {

    void get(I item, I2 item2);

}
