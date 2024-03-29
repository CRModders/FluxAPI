package dev.crmodders.flux.mixins.logging;

import dev.crmodders.flux.logger.LogWrapper;
import finalforeach.cosmicreach.gamestates.GameState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.PrintStream;

@Mixin(GameState.class)
public class GameStateMixin {
    private static String TAG = "\u001B[35;1m{GameState}\u001B[0m\u001B[37m:";

    @Redirect(method = "switchToGameState", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"))
    private static void printCapture(PrintStream instance, String x) {
        LogWrapper.info("%s %s".formatted(TAG, x));
    }

}
