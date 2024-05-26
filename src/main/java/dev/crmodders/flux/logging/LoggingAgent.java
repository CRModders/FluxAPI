package dev.crmodders.flux.logging;

import dev.crmodders.flux.logging.api.MicroLogger;
import dev.crmodders.flux.logging.impl.CustomizableLogger;
import dev.crmodders.flux.logging.impl.SimpleLogger;
import dev.crmodders.flux.util.AnsiColours;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class LoggingAgent {

    static Map<String, MicroLogger> loggerCache = new HashMap<>();
    static Class<? extends MicroLogger> defaultLoggingClass = SimpleLogger.class;

    static CustomizableLogger logger = LoggingAgent.getLogger("FluxApi / LoggingAgent", CustomizableLogger.class);
    static {
        logger.setTagColorizer((tag) -> AnsiColours.CYAN + "["+tag+"]:" + AnsiColours.RESET);
        logger.setMessageColorizer((level, msg) -> msg);
    }

    public static void setDefaultLoggingClass(Class<? extends MicroLogger> loggingClass) {
        logger.info("Changing Default Logging Class From {} to {}", defaultLoggingClass.getName(), loggingClass.getName());
        defaultLoggingClass = loggingClass;
    }

    static <T extends MicroLogger> boolean isClassInCache(String name, Class<T> loggingClass) {
        if (loggerCache.get(name) != null) {
            return loggerCache.get(name).getClass() != loggingClass;
        }
        return false;
    }

    static <T extends MicroLogger> boolean isLoggerInCache(String name, Class<T> loggingClass) {
        return loggerCache.containsKey(name);
    }

    static <T extends MicroLogger> T getLoggerAsWrapper(MicroLogger innerLogger, Class<T> loggingClass) {
        T logger;
        try {
            logger = loggingClass.getConstructor(MicroLogger.class).newInstance(innerLogger);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return logger;
    }

    public static <T extends MicroLogger> T getLogger(String name, Class<T> loggingClass) {
        if (isClassInCache(name, loggingClass)) return (T) loggerCache.get(name);
        if (isLoggerInCache(name, loggingClass)) return getLoggerAsWrapper(loggerCache.get(name), loggingClass);
        T logger;
        try {
            logger = loggingClass.getConstructor(String.class).newInstance(name);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        loggerCache.put(name, logger);
        return logger;
    }

    public static MicroLogger shallowCloneLoggerAs(String name, String newName) {
        MicroLogger logger = getLogger(name);
        return logger.shallowClone(newName);
    }

    public static MicroLogger shallowCloneLoggerAs(String name, MicroLogger logger) {
        logger.shallowClone(name);
        return logger;
    }

    public static MicroLogger getLogger(String name) {
        return getLogger(name, defaultLoggingClass);
    }

    public static void clearCache() {
        logger.info("Clearing Logger Cache: Item Count {}", loggerCache.keySet().size());
        loggerCache.clear();
    }

}
