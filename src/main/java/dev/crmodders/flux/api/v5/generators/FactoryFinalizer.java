package dev.crmodders.flux.api.v5.generators;

import dev.crmodders.flux.api.v5.suppliers.ReturnableSupplier;

/**
 * A helper class that just runs after everything else is loaded to make sure everything is working correctly
 * */
public class FactoryFinalizer<T> {

        ReturnableSupplier<T> finalizer;

        public FactoryFinalizer(ReturnableSupplier<T> finalizer) {
            this.finalizer = finalizer;
        }

        public T finalizeFactory() {
            return finalizer.get();
        }

}