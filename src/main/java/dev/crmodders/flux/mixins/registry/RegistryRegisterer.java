package dev.crmodders.flux.mixins.registry;

import dev.crmodders.flux.engine.GameLoader;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.gamestates.GameState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockGame.class)
public class RegistryRegisterer {

    @Inject(method = "create", at = @At("TAIL"))
    private void create(CallbackInfo ci) {
        GameState.switchToGameState(new GameLoader());
    }

}
