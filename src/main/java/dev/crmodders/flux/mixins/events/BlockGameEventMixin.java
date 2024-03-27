package dev.crmodders.flux.mixins.events;

import dev.crmodders.flux.api.events.GameEvents;
import finalforeach.cosmicreach.BlockGame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockGame.class)
public class BlockGameEventMixin {
    @Inject(method = "runTicks", at= @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/world/blockevents/ScheduledTrigger;runScheduledTriggers()V", shift = At.Shift.AFTER))
    private void afterGameTick(CallbackInfo ci) {
        GameEvents.AFTER_GAME_IS_TICKED.invoker().onTick();
    }

    @Inject(method = "runTicks", at= @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/GameState;update(F)V", shift = At.Shift.BEFORE))
    private void beforeGameTick(CallbackInfo ci) {
        GameEvents.BEFORE_GAME_IS_TICKED.invoker().onTick();
    }
}
