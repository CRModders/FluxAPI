package dev.crmodders.flux.mixins.logging;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationLogger;
import org.pmw.tinylog.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.PrintStream;

@Mixin(Lwjgl3ApplicationLogger.class)
public class LLwjgl3ApplicationLoggerMixin implements ApplicationLogger {

    @Override
    public void log(String tag, String msg) {
        Logger.info("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
    }

    @Override
    public void log(String tag, String msg, Throwable throwable) {
        Logger.info("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
        throwable.printStackTrace(System.out);
    }

    @Override
    public void error(String tag, String msg) {
        Logger.error("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
    }

    @Override
    public void error(String tag, String msg, Throwable throwable) {
        Logger.error("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
        throwable.printStackTrace(System.err);
    }

    @Override
    public void debug(String tag, String msg) {
        Logger.debug("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
    }

    @Override
    public void debug(String tag, String msg, Throwable throwable) {
        Logger.debug("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
        throwable.printStackTrace(System.out);
    }
}
