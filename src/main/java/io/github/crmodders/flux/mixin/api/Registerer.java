package io.github.crmodders.flux.mixin.api;

import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import io.github.crmodders.flux.FluxAPI;
import io.github.crmodders.flux.api.blocks.ModBlock;
import io.github.crmodders.flux.api.registries.BlockReg;
import io.github.crmodders.flux.api.registries.BuiltInRegistries;
import io.github.crmodders.flux.api.registries.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainMenu.class)
public class Registerer extends GameState {

    @Inject(method = "create", at = @At("HEAD"))
    private void create(CallbackInfo ci) {
        BlockReg.create(ci);
    }

}