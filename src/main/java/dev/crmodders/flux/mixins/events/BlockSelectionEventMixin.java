package dev.crmodders.flux.mixins.events;

import dev.crmodders.flux.api.events.GameEvents;
import finalforeach.cosmicreach.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.world.BlockSelection;
import finalforeach.cosmicreach.world.Zone;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(BlockSelection.class)
public class BlockSelectionEventMixin {
    @Inject(method = "breakBlock", at=@At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    void afterBreakCallback(Zone zone, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci, BlockState blockState, BlockEventTrigger[] triggers, Map args, int i) {
        GameEvents.AFTER_BLOCK_IS_BROKEN.invoker().onBlockBroken(zone, blockPos, timeSinceLastInteract);
    }

    @Inject(method = "breakBlock", at=@At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    void beforeBreakCallback(Zone zone, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci) {
        GameEvents.BEFORE_BLOCK_IS_BROKEN.invoker().onBlockBroken(zone, blockPos, timeSinceLastInteract);
    }

    @Inject(method = "placeBlock", at=@At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    void afterPlaceCallback(Zone zone, BlockState targetBlockState, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci) {
        GameEvents.AFTER_BLOCK_IS_PLACED.invoker().onBlockPlaced(zone, targetBlockState, blockPos, timeSinceLastInteract);
    }

    @Inject(method = "placeBlock", at=@At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    void beforePlaceCallback(Zone zone, BlockState targetBlockState, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci) {
        GameEvents.BEFORE_BLOCK_IS_PLACED.invoker().onBlockPlaced(zone, targetBlockState, blockPos, timeSinceLastInteract);
    }
}