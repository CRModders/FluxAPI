package dev.crmodders.flux.mixins.logging;



import finalforeach.cosmicreach.gamestates.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.PrintStream;

@Mixin(GameState.class)
public class GameStateMixin {
    @Shadow public static GameState currentGameState;
    @Unique private static final Logger LOGGER = LoggerFactory.getLogger("CosmicReach / GameState");

    @Redirect(method = "switchToGameState", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"))
    private static void printCapture(PrintStream instance, String x, GameState gameState) {
        if(currentGameState == null) {
            LOGGER.info("Switched to GameState: \u001B[31m{}\u001B[37m", gameState.getClass().getSimpleName());
        } else {
            LOGGER.info("Switched from \u001B[31m{}\u001B[37m to \u001B[31m{}\u001B[37m", currentGameState.getClass().getSimpleName(), gameState.getClass().getSimpleName());
        }
    }

}
