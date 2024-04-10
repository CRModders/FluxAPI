package dev.crmodders.flux.mixins.registry;

import com.badlogic.gdx.utils.Array;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockState;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

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
