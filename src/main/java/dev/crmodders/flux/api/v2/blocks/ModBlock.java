package dev.crmodders.flux.api.v2.blocks;

import dev.crmodders.flux.api.v5.suppliers.ReturnableInputSupplier;
import finalforeach.cosmicreach.blocks.Block;

public class ModBlock {
    public Block block;

    public ModBlock() {}
    public ModBlock(ReturnableInputSupplier<ModBlock, Block> supplier) {
        this.block = supplier.get(this);
    }

}