package dev.crmodders.flux.logging.impl;

import dev.crmodders.flux.logging.api.MicroLogger;
import dev.crmodders.flux.logging.impl.SimpleLogger;
import dev.crmodders.flux.util.AnsiColours;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CustomizableLogger extends SimpleLogger implements MicroLogger {

    public static Map<Level, String> basicColors = new HashMap<>();

    static {
        basicColors.put(Level.TRACE, AnsiColours.PURPLE);
        basicColors.put(Level.DEBUG, AnsiColours.BRIGHT_BLACK);
        basicColors.put(Level.INFO, AnsiColours.BLUE);
        basicColors.put(Level.WARN, AnsiColours.BRIGHT_YELLOW);
        basicColors.put(Level.ERROR, AnsiColours.BRIGHT_RED);
    }

    public static Function<Level, String> BasicLevelColorizer = (level) -> basicColors.get(level) + "[" + level + "]" + AnsiColours.RESET;
    public static Function<Level, String> ColorlessLevelColorizer = (level) -> "[" + level + "]";

    public static Function<String, String> BasicTagColorizer = (tag) -> AnsiColours.GREEN + "[" + tag + "]" + AnsiColours.RED + ":" + AnsiColours.RESET;
    public static Function<String, String> ColorlessTagColorizer = (tag) -> "[" + tag + "]:";

    public static BiFunction<Level, String, String> BasicMessageColorizer = (level, formattedMsg) -> switch (level) {
        case ERROR, WARN -> basicColors.get(level) + formattedMsg + AnsiColours.RESET;
        default -> AnsiColours.BRIGHT_BLACK + formattedMsg + AnsiColours.RESET;
    };
    public static BiFunction<Level, String, String> ColorlessMessageColorizer = (level, formattedMsg) -> switch (level) {
        case ERROR, WARN -> basicColors.get(level) + formattedMsg + AnsiColours.RESET;
        default -> AnsiColours.BRIGHT_BLACK + formattedMsg + AnsiColours.RESET;
    };

    Function<Level, String> levelColorizer;
    Function<String, String> tagColorizer;
    BiFunction<Level, String, String> messageColorizer;

    public CustomizableLogger(String tag) {
        super(tag);

        setLevelColorizer(BasicLevelColorizer);
        setTagColorizer(BasicTagColorizer);
        setMessageColorizer(BasicMessageColorizer);
    }

    public CustomizableLogger(MicroLogger logger) {
        super(logger.getTag());

        setLevelColorizer((level) -> basicColors.get(level) + "[" + level + "]" + AnsiColours.RESET);
        setTagColorizer((tagName) -> AnsiColours.GREEN + "[" + tagName + "]" + AnsiColours.RED + ":" + AnsiColours.RESET);
        setMessageColorizer((level, formattedMsg) -> switch (level) {
            case ERROR, WARN -> basicColors.get(level) + formattedMsg + AnsiColours.RESET;
            default -> AnsiColours.BRIGHT_BLACK + formattedMsg + AnsiColours.RESET;
        });
    }

    public void setLevelColorizer(Function<Level, String> levelColorizer) {
        this.levelColorizer = levelColorizer;
    }

    public void setTagColorizer(Function<String, String> tagColorizer) {
        this.tagColorizer = tagColorizer;
    }

    public void setMessageColorizer(BiFunction<Level, String, String> messageColorizer) {
        this.messageColorizer = messageColorizer;
    }

    @Override
    public void log(Level level, String msg) {
        if (!useSl4j) {
            System.out.println(MessageFormatter.basicArrayFormat(basicLogFormat, new Object[]{
                    levelColorizer.apply(level),
                    tagColorizer.apply(tag),
                    messageColorizer.apply(level, msg)
            }));
            return;
        }
        sl4jLogger.atLevel(level).log(msg);
    }

    @Override
    public MicroLogger shallowClone(String tag) {
        CustomizableLogger logger = new CustomizableLogger(tag);
        logger.sl4jLogger = LoggerFactory.getLogger(tag);
        logger.setMessageColorizer(messageColorizer);
        logger.setLevelColorizer(levelColorizer);
        logger.setTagColorizer(tagColorizer);
        logger.innerLogger = innerLogger;
        logger.useInnerLogger = useInnerLogger;
        return logger;
    }

}
