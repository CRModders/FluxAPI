package dev.crmodders.flux.mixins.registry;

import finalforeach.cosmicreach.blocks.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public class BlockRegisterer {

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lfinalforeach/cosmicreach/blocks/Block;getInstance(Ljava/lang/String;)Lfinalforeach/cosmicreach/blocks/Block;",
                    ordinal = 0
            )
    )
    private static Block dummy0(String blockName) {
        return null;
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lfinalforeach/cosmicreach/blocks/Block;getInstance(Ljava/lang/String;)Lfinalforeach/cosmicreach/blocks/Block;",
                    ordinal = 1
            )
    )
    private static Block dummy1(String blockName) {
        return null;
    }

}
