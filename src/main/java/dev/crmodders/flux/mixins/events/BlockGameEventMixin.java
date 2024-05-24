package dev.crmodders.flux.mixins.events;

import finalforeach.cosmicreach.BlockGame;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockGame.class)
public class BlockGameEventMixin {
//    @Inject(method = "runTicks", at = @At("TAIL"))
//    private void afterGameTick(CallbackInfo ci) {
//        GameEvents.AFTER_GAME_IS_TICKED.invoker().onTick();
//    }
//
//    @Inject(method = "runTicks", at= @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/GameState;update(F)V", shift = At.Shift.BEFORE))
//    private void beforeGameTick(CallbackInfo ci) {
//        GameEvents.BEFORE_GAME_IS_TICKED.invoker().onTick();
//    }
}
