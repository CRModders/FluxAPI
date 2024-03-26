package dev.crmodders.flux.mixins.logging;

import finalforeach.cosmicreach.rendering.shaders.GameShader;
import org.pmw.tinylog.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.PrintStream;

@Mixin(GameShader.class)
public class GameShaderMixin {
    private static String TAG = "\u001B[35;1m{Shaders}\u001B[0m\u001B[37m";

    @Redirect(method = "reload", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"))
    private void print1(PrintStream instance, String x) {
        Logger.info("%s: %s".formatted(TAG, x));
    }

    @Redirect(method = "reloadAllShaders", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"))
    private static void print2(PrintStream instance, String x) {
        Logger.info("%s: %s".formatted(TAG, x));
    }

}
