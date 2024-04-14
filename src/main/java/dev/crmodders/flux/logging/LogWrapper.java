package dev.crmodders.flux.logging;

import org.pmw.tinylog.Logger;

import java.text.MessageFormat;
import java.util.logging.Level;

public class LogWrapper {

    public static boolean isQuilt;
    public static String TAG = "\u001B[35;1m{FluxLogger}\u001B[0m\u001B[37m";
    static java.util.logging.Logger logger;

    public static void init() {
        logger = java.util.logging.Logger.getLogger("FluxAPI");
        try {
            LogWrapper.class.getClassLoader().loadClass("org.quiltmc.loader.impl.launch.knot.Knot");
            isQuilt = true;
            info("%s: Quilt Detected, Switching Loggers".formatted(TAG));
        } catch (Exception ignore) {
            isQuilt = false;
        }
    }

    public static void info(String out) {
        if (isQuilt) {
            logger.info(out);
            return;
        }
        Logger.info(out);
    }

    public static void info(String out, Object... objects) {
        String formattedMessage = fixFormat(out, objects);
        if (isQuilt) {
            logger.info(formattedMessage);
            return;
        }
        Logger.info(out);
    }

    public static void warn(String out) {
        if (isQuilt) {
            logger.warning(out);
            return;
        }
        Logger.warn(out);
    }

    public static void warn(String out, Object... objects) {
        String formattedMessage = fixFormat(out, objects);
        if (isQuilt) {
            logger.warning(formattedMessage);
            return;
        }
        Logger.warn(out);
    }

    public static void error(String out) {
        if (isQuilt) {
            logger.log(Level.SEVERE, out);
            return;
        }
        Logger.error(out);
    }

    public static void error(String out, Object... objects) {
        String formattedMessage = fixFormat(out, objects);
        if (isQuilt) {
            logger.log(Level.SEVERE, formattedMessage);
            return;
        }
        Logger.error(formattedMessage);
    }

    public static void debug(String out) {
        if (isQuilt) {
            logger.log(Level.FINE, out);
            return;
        }
        Logger.debug(out);
    }

    public static void debug(String out, Object... objects) {
        String formattedMessage = fixFormat(out, objects);
        if (isQuilt) {
            logger.log(Level.FINE, formattedMessage);
            return;
        }
        Logger.debug(formattedMessage);
    }

    static String fixFormat(String out, Object... objects) {
        return out.replaceAll("\\{}", "%s").formatted(objects);
    }

}
