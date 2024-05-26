package dev.crmodders.flux;

import dev.crmodders.flux.events.OnRegisterLanguageEvent;
import dev.crmodders.flux.logging.LoggingAgent;
import dev.crmodders.flux.logging.impl.CustomizableLogger;
import dev.crmodders.flux.logging.impl.SimpleColouredLogger;
import net.appel.mod.interfaces.ModInitializer;
import org.greenrobot.eventbus.Subscribe;

public class FluxAppel implements ModInitializer {

    public static final CustomizableLogger LOGGER = LoggingAgent.getLogger("FluxAPIAppel", CustomizableLogger.class);

    @Override
    public void onInit() {
        LOGGER.setMessageColorizer((level, string) -> string);
        LoggingAgent.setDefaultLoggingClass(SimpleColouredLogger.class);

        LOGGER.info("Flux Appel Edition Initialized");
        FluxRegistries.EVENT_BUS.register(this);
    }

    @Subscribe
    public void onEvent(OnRegisterLanguageEvent event) {
        event.registerLanguage(FluxConstants.LanguageEnUs.load());
    }

    @Override
    public String getModId() {
        return "fluxapi";
    }
}
