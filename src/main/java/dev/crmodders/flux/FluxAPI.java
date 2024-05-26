package dev.crmodders.flux;

import dev.crmodders.flux.events.OnRegisterLanguageEvent;
import dev.crmodders.flux.logging.LoggingAgent;
import dev.crmodders.flux.logging.impl.CustomizableLogger;
import dev.crmodders.flux.logging.impl.SimpleColouredLogger;
import net.fabricmc.api.ModInitializer;
import org.greenrobot.eventbus.Subscribe;

public class FluxAPI implements ModInitializer {

    public static final CustomizableLogger LOGGER = LoggingAgent.getLogger("FluxAPI", CustomizableLogger.class);

    @Override
    public void onInitialize() {
        LOGGER.setMessageColorizer((level, string) -> string);
        LoggingAgent.setDefaultLoggingClass(SimpleColouredLogger.class);

        LOGGER.info("Flux Initialized");
        FluxRegistries.EVENT_BUS.register(this);
    }

    @Subscribe
    public void onEvent(OnRegisterLanguageEvent event) {
        event.registerLanguage(FluxConstants.LanguageEnUs.load());
    }

}
