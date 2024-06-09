package dev.crmodders.flux.mixins.registry;

import finalforeach.cosmicreach.blocks.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(Block.class)
public class BlockRegisterer {

    @Shadow @Final public static Map<String, Block> blocksByName;

    /**
     * @author nanobass
     * @reason is replaced by flux, allows for much more advanced features
     *          than data modding
     */
    @Overwrite
    public static Block getInstance(String blockName) {
        return blocksByName.get(blockName);
    }

}
