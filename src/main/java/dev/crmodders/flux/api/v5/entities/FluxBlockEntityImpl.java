package dev.crmodders.flux.api.v5.entities;

import dev.crmodders.flux.api.v5.entities.api.IFluxBlockEntity;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.vectors.Vec3;
import finalforeach.cosmicreach.blockentities.BlockEntity;

public class FluxBlockEntityImpl extends BlockEntity implements IFluxBlockEntity {

    Identifier id;
    Vec3 pos;

    public FluxBlockEntityImpl(Identifier id, Vec3 location) {
        super();
        this.id = id;
        this.pos = location;
    }

    @Override
    public Vec3 getPos() {
        return pos;
    }

    @Override
    public Identifier getId() {
        return id;
    }

}
