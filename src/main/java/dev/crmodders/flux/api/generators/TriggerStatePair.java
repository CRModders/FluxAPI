package dev.crmodders.flux.api.generators;

import dev.crmodders.flux.api.generators.suppliers.BasicTriggerSupplier;

record TriggerStatePair(
        String blockStateName,
        BasicTriggerSupplier triggerSupplier
) {}
