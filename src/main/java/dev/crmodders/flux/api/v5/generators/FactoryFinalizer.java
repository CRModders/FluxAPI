package dev.crmodders.flux.api.v5.generators;

import java.util.function.Supplier;

/**
 * A helper class that just runs after everything else is loaded to make sure everything is working correctly
 * */
public class FactoryFinalizer<T> {

    Supplier<T> finalizer;

        public FactoryFinalizer(Supplier<T> finalizer) {
            this.finalizer = finalizer;
        }

        public T finalizeFactory() {
            return finalizer.get();
        }

}