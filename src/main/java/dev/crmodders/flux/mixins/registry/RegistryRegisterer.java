package dev.crmodders.flux.mixins.registry;

import dev.crmodders.flux.engine.GameLoader;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.gamestates.GameState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockGame.class)
public class RegistryRegisterer {

    @Shadow public static boolean gameStarted;

    @Inject(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ClientSingletons;create()V", shift = At.Shift.AFTER), cancellable = true)
    private void create(CallbackInfo ci) {
        GameState.switchToGameState(new GameLoader());
        ci.cancel();
        gameStarted = true;
    }

}
