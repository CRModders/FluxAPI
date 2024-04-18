package dev.crmodders.flux.api.v5.suppliers;

@FunctionalInterface
public interface DoubleInputSupplier<I, I2> {

    void get(I item, I2 item2);

}
