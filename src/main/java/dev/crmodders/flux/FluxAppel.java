package dev.crmodders.flux;

import dev.crmodders.flux.api.events.GameEvents;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.localization.TranslationApi;
import net.appel.mod.interfaces.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxAppel implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("FluxAPI");

    @Override
    public void onInit() {
        LOGGER.info("Flux Fabric Initialized");

        GameEvents.ON_REGISTER_LANGUAGE.register(() -> {
            LanguageFile lang = LanguageFile.loadLanguageFile(FluxConstants.LanguageEnUs.load());
            TranslationApi.registerLanguageFile(lang);
        });
    }

    @Override
    public String getModId() {
        return "fluxapi";
    }
}
