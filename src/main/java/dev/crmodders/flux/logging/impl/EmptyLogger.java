package dev.crmodders.flux.logging.impl;

import dev.crmodders.flux.logging.api.MicroLogger;
import dev.crmodders.flux.util.LoaderDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.InvocationTargetException;

public class EmptyLogger extends SimpleLogger implements MicroLogger {

    public EmptyLogger(String tag) {
        super(tag);
    }

    @Override
    public void log(Level level, String msg) {}

}
