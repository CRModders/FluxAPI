package dev.crmodders.flux.mixins.logging;


import finalforeach.cosmicreach.BlockGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.PrintStream;

@Mixin(BlockGame.class)
public class BlockGameMixin {

    @Unique
    private static final Logger logger = LoggerFactory.getLogger("CosmicReach / BlockGame");

    @Redirect(method = "dispose", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"))
    private void print1(PrintStream instance, String x) {
        logger.info(x);
    }

    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"))
    private void print2(PrintStream instance, String x) {
        logger.info(x);
    }

}
