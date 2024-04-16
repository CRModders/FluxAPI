package dev.crmodders.flux;

import dev.crmodders.flux.api.block.impl.TestBlock;
import dev.crmodders.flux.api.events.GameEvents;
import dev.crmodders.flux.api.factories.FactoryFinalizer;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.registry.FluxRegistries;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxAPI implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("FluxAPI");

    @Override
    public void onInitialize() {
        LOGGER.info("Flux Fabric Initialized");

        FluxRegistries.BLOCK_FACTORIES.add(new FactoryFinalizer<>(TestBlock::new));

        GameEvents.ON_REGISTER_LANGUAGE.register(() -> {
            LanguageFile lang = LanguageFile.loadLanguageFile(FluxConstants.LanguageEnUs.load());
            TranslationApi.registerLanguageFile(lang);
        });

    }
}
