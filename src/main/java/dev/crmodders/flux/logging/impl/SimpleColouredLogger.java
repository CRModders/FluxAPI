package dev.crmodders.flux.logging.impl;

import dev.crmodders.flux.logging.api.MicroLogger;
import dev.crmodders.flux.util.AnsiColours;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

public class SimpleColouredLogger extends SimpleLogger implements MicroLogger {
    public SimpleColouredLogger(String tag) {
        super(tag);
    }

    @Override
    public void log(Level level, String msg) {
        if (!useSl4j) {
            System.out.println(MessageFormatter.basicArrayFormat(basicLogFormat, new Object[]{
                    CustomizableLogger.basicColors.get(level) + "[" + level + "]" + AnsiColours.RESET,
                    AnsiColours.GREEN + "[" + tag + "]" + AnsiColours.RED + ":" + AnsiColours.RESET,
                    switch (level) {
                        case ERROR, WARN -> CustomizableLogger.basicColors.get(level) + msg + AnsiColours.RESET;
                        default -> AnsiColours.BRIGHT_BLACK + msg + AnsiColours.RESET;
                    }
            }));
            return;
        }
        sl4jLogger.atLevel(level).log(msg);
    }

}
