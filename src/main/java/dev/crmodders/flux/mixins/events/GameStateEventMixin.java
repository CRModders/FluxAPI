package dev.crmodders.flux.mixins.events;

import finalforeach.cosmicreach.gamestates.GameState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameState.class)
public class GameStateEventMixin {
//    @Inject(method = "switchToGameState", at=@At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
//    private static void afterSwitchToGameState(GameState gameState, CallbackInfo ci) {
//        GameEvents.AFTER_GAMESTATE_CHANGED.invoker().onStateChanged(gameState);
//    }
//
//    @Inject(method = "switchToGameState", at=@At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
//    private static void beforeSwitchToGameState(GameState gameState, CallbackInfo ci) {
//        GameEvents.BEFORE_GAMESTATE_CHANGED.invoker().onStateChanged(gameState);
//    }
}