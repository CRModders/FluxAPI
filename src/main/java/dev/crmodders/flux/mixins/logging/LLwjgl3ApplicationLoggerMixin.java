package dev.crmodders.flux.mixins.logging;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;

@Mixin(Lwjgl3ApplicationLogger.class)
public class LLwjgl3ApplicationLoggerMixin implements ApplicationLogger {

    @Unique
    private static final Map<String, Logger> cache = new HashMap<>();

    @Unique
    private static Logger taggedLogger(String tag) {
        if (cache.containsKey(tag)) return cache.get(tag);
        Logger logger = LoggerFactory.getLogger("CosmicReach / " + tag);
        cache.put(tag, logger);
        return logger;
    }

    @Override
    public void log(String tag, String msg) {
        taggedLogger(tag).info(msg);
    }

    @Override
    public void log(String tag, String msg, Throwable throwable) {
        taggedLogger(tag).info(msg, throwable);
    }

    @Override
    public void error(String tag, String msg) {
        taggedLogger(tag).error(msg);
    }

    @Override
    public void error(String tag, String msg, Throwable throwable) {
        taggedLogger(tag).error(msg, throwable);
    }

    @Override
    public void debug(String tag, String msg) {
        taggedLogger(tag).debug(msg, msg);
    }

    @Override
    public void debug(String tag, String msg, Throwable throwable) {
        taggedLogger(tag).debug(msg, msg, throwable);
    }
}
