package dev.crmodders.flux.mixins.logging;

import com.badlogic.gdx.assets.AssetManager;
import dev.crmodders.flux.FluxAPI;
import finalforeach.cosmicreach.BlockGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.PrintStream;
import java.util.List;

import static dev.crmodders.flux.assets.FluxGameAssetLoader.LOADER;

@Mixin(BlockGame.class)
public class BlockGameMixin {

    @Unique
    private static final Logger logger = LoggerFactory.getLogger("CosmicReach / BlockGame");

    @Redirect(method = "dispose", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"), require = 0)
    private void print1(PrintStream instance, String x) {
        logger.info("\u001B[36m{}\u001B[37m", x);
    }

    @Redirect(method = "printGLInfo", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"), require = 0)
    private void print2(PrintStream instance, String x) {
        List<String> lines = x.lines().toList();
        for(String line : lines) {
            logger.info("\u001B[36m{}\u001B[37m", line);
        }
    }

    @Inject(method = "dispose", at = @At(value = "INVOKE", target = "Ljava/lang/System;exit(I)V", shift = At.Shift.BEFORE), require = 0)
    public void dispose(CallbackInfo ci) {
        AssetManager manager = LOADER.getAssetManager();
        manager.dispose();
        FluxAPI.LOGGER.info("Flux Destroyed");
    }

}
