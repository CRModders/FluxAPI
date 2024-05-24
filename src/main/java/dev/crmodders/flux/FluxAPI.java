package dev.crmodders.flux;

import dev.crmodders.flux.api.v5.events.GameEvents;
import dev.crmodders.flux.api.v5.generators.BlockGenerator;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import net.fabricmc.api.ModInitializer;

public class FluxAPI implements ModInitializer {

    @Override
    public void onInitialize() {
        LogWrapper.init();
        LogWrapper.info("Flux Fabric Initialized");

        GameEvents.ON_REGISTER_LANGUAGE.register(() -> {
            LanguageFile lang = LanguageFile.loadLanguageFile(FluxConstants.LanguageEnUs.load());
            TranslationApi.registerLanguageFile(lang);
        });


    }
}
