package dev.crmodders.flux.api.v5.generators;

import dev.crmodders.flux.api.v5.generators.suppliers.BasicTriggerSupplier;

record TriggerStatePair(
        String blockStateName,
        BasicTriggerSupplier triggerSupplier
) {}
