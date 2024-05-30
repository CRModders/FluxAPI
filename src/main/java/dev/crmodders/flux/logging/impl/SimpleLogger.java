package dev.crmodders.flux.logging.impl;

import dev.crmodders.flux.logging.api.MicroLogger;
import dev.crmodders.flux.util.LoaderDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SimpleLogger implements MicroLogger {

    String tag;
    Logger sl4jLogger;
    MicroLogger innerLogger;
    boolean useSl4j;
    boolean useInnerLogger;

    public SimpleLogger(String tag) {
        this.tag = tag;

        if (LoaderDetector.isOnQuiltBasedLoader() || LoaderDetector.isOnPuzzleLoader()) {
            sl4jLogger = LoggerFactory.getLogger(tag);
            useSl4j = true;
        }
    }

    public SimpleLogger(MicroLogger innerLogger) {
        this(innerLogger.getTag());
        this.innerLogger = innerLogger;
        useInnerLogger = true;
    }

    @Override
    public void log(Level level, String msg) {
        if (useInnerLogger) {
            innerLogger.info(msg);
            return;
        }

        if (!useSl4j) {
            System.out.println(MessageFormatter.basicArrayFormat(basicLogFormat, new Object[]{
                    "[" + level + "]",
                    "[" + tag + "]",
                    msg
            }));
            return;
        }
        sl4jLogger.atLevel(level).log(msg);
    }

    @Override
    public void log(Level level, String msg, Object... args) {
        log(level, MessageFormatter.basicArrayFormat(msg, args));
    }

    @Override
    public void log(Level level, String msg, Throwable throwable) {
        log(level, MessageFormatter.basicArrayFormat(msg, new Object[]{throwable}));
    }

    @Override
    public void trace(String msg) {
        log(Level.TRACE, msg);
    }

    @Override
    public void trace(String msg, Object... args) {
        log(Level.TRACE, msg, args);
    }

    @Override
    public void trace(String msg, Throwable throwable) {
        log(Level.TRACE, msg, throwable);
    }

    @Override
    public void debug(String msg) {
        log(Level.DEBUG, msg);
    }

    @Override
    public void debug(String msg, Object... args) {
        log(Level.DEBUG, msg, args);
    }

    @Override
    public void debug(String msg, Throwable throwable) {
        log(Level.DEBUG, msg, throwable);
    }

    @Override
    public void info(String msg) {
        log(Level.INFO, msg);
    }

    @Override
    public void info(String msg, Object... args) {
        log(Level.INFO, msg, args);
    }

    @Override
    public void info(String msg, Throwable throwable) {
        log(Level.INFO, msg, throwable);
    }

    @Override
    public void warn(String msg) {
        log(Level.WARN, msg);
    }

    @Override
    public void warn(String msg, Object... args) {
        log(Level.WARN, msg, args);
    }

    @Override
    public void warn(String msg, Throwable throwable) {
        log(Level.WARN, msg, throwable);
    }

    @Override
    public void error(String msg) {
        log(Level.ERROR, msg);
    }

    @Override
    public void error(String msg, Object... args) {
        log(Level.ERROR, msg, args);
    }

    @Override
    public void error(String msg, Throwable throwable) {
        log(Level.ERROR, msg, throwable);
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public MicroLogger shallowClone(String name) {
        MicroLogger logger;
        try {
            logger = this.getClass().getConstructor(String.class).newInstance(name);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return logger;
    }
}
