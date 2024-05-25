package dev.crmodders.flux.mixins.logging;


import finalforeach.cosmicreach.gamestates.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.PrintStream;

@Mixin(GameState.class)
public class GameStateMixin {
    @Unique
    private static final Logger logger = LoggerFactory.getLogger("CosmicReach / GameState");

    @Redirect(method = "switchToGameState", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"))
    private static void printCapture(PrintStream instance, String x) {
        logger.info(x);
    }

}
