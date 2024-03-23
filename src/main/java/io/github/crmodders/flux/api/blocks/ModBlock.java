package io.github.crmodders.flux.api.blocks;

import finalforeach.cosmicreach.world.blockevents.BlockEvents;
import finalforeach.cosmicreach.world.blocks.Block;
import io.github.crmodders.flux.api.generators.data.BlockSupplier;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class ModBlock {
    public Block block;

    public ModBlock() {}
    public ModBlock(BlockSupplier supplier) {
        this.block = supplier.getAsBlock(this);
    }

}
