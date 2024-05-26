package dev.crmodders.flux.logging.api;

import org.slf4j.event.Level;

public interface MicroLogger {

    String basicLogFormat = "{} {} {}";

    void log(Level level, String msg);
    void log(Level level, String msg, Object... args);
    void log(Level level, String msg, Throwable throwable);

    void trace(String msg);
    void trace(String msg, Object... args);
    void trace(String msg, Throwable throwable);

    void debug(String msg);
    void debug(String msg, Object... args);
    void debug(String msg, Throwable throwable);

    void info(String msg);
    void info(String msg, Object... args);
    void info(String msg, Throwable throwable);

    void warn(String msg);
    void warn(String msg, Object... args);
    void warn(String msg, Throwable throwable);

    void error(String msg);
    void error(String msg, Object... args);
    void error(String msg, Throwable throwable);

    MicroLogger shallowClone(String name);

    String getTag();

}
