package dev.crmodders.flux.api.factories;

import dev.crmodders.flux.api.suppliers.ReturnableSupplier;

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