package dev.crmodders.flux.mixins.events;

import dev.crmodders.flux.api.events.blocks.BlockEvents;
import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.BlockSelection;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.world.blocks.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(BlockSelection.class)
public class BlockSelectionMixin {
    @Inject(method = "breakBlock", at=@At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    void afterBreakCallback(World world, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci, BlockState blockState, BlockEventTrigger[] triggers, Map args, int i) {
        BlockEvents.AFTER_BLOCK_BREAK.invoker().blockBreak(world, blockPos, timeSinceLastInteract);
    }

    @Inject(method = "breakBlock", at=@At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    void beforeBreakCallback(World world, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci) {
        BlockEvents.BEFORE_BLOCK_BREAK.invoker().blockBreak(world, blockPos, timeSinceLastInteract);
    }

    @Inject(method = "placeBlock", at=@At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    void afterPlaceCallback(World world, BlockState targetBlockState, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci) {
        BlockEvents.BEFORE_BLOCK_PLACE.invoker().blockPlace(world, targetBlockState, blockPos, timeSinceLastInteract);
    }

    @Inject(method = "placeBlock", at=@At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    void beforePlaceCallback(World world, BlockState targetBlockState, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci) {
        BlockEvents.AFTER_BLOCK_PLACE.invoker().blockPlace(world, targetBlockState, blockPos, timeSinceLastInteract);
    }
}