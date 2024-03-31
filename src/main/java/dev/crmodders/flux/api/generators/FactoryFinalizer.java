package dev.crmodders.flux.api.generators;

import dev.crmodders.flux.api.suppliers.ReturnableSupplier;

public class FactoryFinalizer<T> {

        ReturnableSupplier<T> finalizer;

        public FactoryFinalizer(ReturnableSupplier<T> finalizer) {
            this.finalizer = finalizer;
        }

        public T finalizeFactory() {
            return finalizer.get();
        }

}