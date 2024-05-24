package dev.crmodders.flux.mixins.events;

import finalforeach.cosmicreach.BlockSelection;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockSelection.class)
public class BlockSelectionEventMixin {
//    @Inject(method = "breakBlock", at=@At("TAIL"))
//    void afterBreakCallback(Zone zone, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci) {
//        GameEvents.AFTER_BLOCK_IS_BROKEN.invoker().onBlockBroken(zone, blockPos, timeSinceLastInteract);
//    }
//
//    @Inject(method = "breakBlock", at=@At("HEAD"))
//    void beforeBreakCallback(Zone zone, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci) {
//        GameEvents.BEFORE_BLOCK_IS_BROKEN.invoker().onBlockBroken(zone, blockPos, timeSinceLastInteract);
//    }
//
//    @Inject(method = "placeBlock", at=@At("TAIL"))
//    void afterPlaceCallback(Zone zone, BlockState targetBlockState, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci) {
//        GameEvents.AFTER_BLOCK_IS_PLACED.invoker().onBlockPlaced(zone, targetBlockState, blockPos, timeSinceLastInteract);
//    }
//
//    @Inject(method = "placeBlock", at=@At("HEAD"))
//    void beforePlaceCallback(Zone zone, BlockState targetBlockState, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci) {
//        GameEvents.BEFORE_BLOCK_IS_PLACED.invoker().onBlockPlaced(zone, targetBlockState, blockPos, timeSinceLastInteract);
//    }
}