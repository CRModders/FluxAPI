package dev.crmodders.flux.mixins.events;

import dev.crmodders.flux.api.events.gamestate.GameStateEvents;
import finalforeach.cosmicreach.gamestates.GameState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameState.class)
public class GameStateMixin {
    @Inject(method = "switchToGameState", at=@At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void afterSwitchToGameState(GameState gameState, CallbackInfo ci) {
        GameStateEvents.AFTER_CHANGE_STATE.invoker().changeState(gameState);
    }

    @Inject(method = "switchToGameState", at=@At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void beforeSwitchToGameState(GameState gameState, CallbackInfo ci) {
        GameStateEvents.BEFORE_CHANGE_STATE.invoker().changeState(gameState);
    }
}