package dev.crmodders.flux.api.v6.suppliers;

@FunctionalInterface
public interface DoubleInputSupplier<I, I2> {

    void get(I item, I2 item2);

}
