package dev.crmodders.flux.api.generators;

import dev.crmodders.flux.api.generators.suppliers.BasicTriggerSupplier;

public record TriggerStatePair(
        String blockStateName,
        BasicTriggerSupplier triggerSupplier
) {}
