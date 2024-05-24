package dev.crmodders.flux.api.v5.entities;

import dev.crmodders.flux.api.v5.entities.api.IFluxBlockEntity;
import finalforeach.cosmicreach.world.Zone;

public record ZoneBlockEntityPair(
        IFluxBlockEntity entity,
        Zone zone
) {}
