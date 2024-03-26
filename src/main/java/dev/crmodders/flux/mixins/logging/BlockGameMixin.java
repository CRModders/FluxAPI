package dev.crmodders.flux.mixins.logging;

import finalforeach.cosmicreach.BlockGame;
import org.pmw.tinylog.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.PrintStream;

@Mixin(BlockGame.class)
public class BlockGameMixin {

    private static String TAG_BLOCKGAME = "\u001B[35;1m{Init}\u001B[0m\u001B[37m";

    @Redirect(method = "dispose", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"))
    private void print1(PrintStream instance, String x) {
        Logger.info("%s: %s".formatted(TAG_BLOCKGAME, x));
    }

    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"))
    private void print2(PrintStream instance, String x) {
        Logger.info("%s: %s".formatted(TAG_BLOCKGAME, x));
    }

}
