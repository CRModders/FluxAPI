package dev.crmodders.flux.mixins.logging;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationLogger;
import dev.crmodders.flux.logging.LogWrapper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Lwjgl3ApplicationLogger.class)
public class LLwjgl3ApplicationLoggerMixin implements ApplicationLogger {

    @Override
    public void log(String tag, String msg) {
        LogWrapper.info("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
    }

    @Override
    public void log(String tag, String msg, Throwable throwable) {
        LogWrapper.info("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
        throwable.printStackTrace(System.out);
    }

    @Override
    public void error(String tag, String msg) {
        LogWrapper.error("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
    }

    @Override
    public void error(String tag, String msg, Throwable throwable) {
        LogWrapper.error("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
        throwable.printStackTrace(System.err);
    }

    @Override
    public void debug(String tag, String msg) {
        LogWrapper.debug("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
    }

    @Override
    public void debug(String tag, String msg, Throwable throwable) {
        LogWrapper.debug("\u001B[35;1m{%s}\u001B[0m\u001B[37m: %s".formatted(tag, msg));
        throwable.printStackTrace(System.out);
    }
}
