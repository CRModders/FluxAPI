package dev.crmodders.flux.mixins.accessor;

import finalforeach.cosmicreach.world.blocks.Block;
import finalforeach.cosmicreach.world.blocks.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Block.class)
public interface BlockAccessor {
    @Invoker("generateSlabs")
    static void generateSlabs(String stateKey, BlockState state) {
        throw new AssertionError();
    }

}