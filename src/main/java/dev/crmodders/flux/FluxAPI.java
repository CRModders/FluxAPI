package dev.crmodders.flux;

import dev.crmodders.flux.api.v6.block.impl.funni.FunniBok;
import dev.crmodders.flux.events.GameEvents;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxAPI implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("FluxAPI");


    @Override
    public void onInitialize() {
        LOGGER.info("Flux Fabric Initialized");

        // TODO remove in release
        FluxRegistries.ON_PRE_INITIALIZE.register(new Identifier(FluxConstants.MOD_ID, ""), () -> {
            FluxRegistries.BLOCK_EVENT_ACTION_FACTORIES.register(new Identifier(FluxConstants.MOD_ID, "action"), TestAction::new);
            FluxRegistries.BLOCK_FACTORIES.add(TestBlock::new);
            FluxRegistries.BLOCK_FACTORIES.add(FunniBok::new);
        });

        GameEvents.ON_REGISTER_LANGUAGE.register(() -> {
            LanguageFile lang = LanguageFile.loadLanguageFile(FluxConstants.LanguageEnUs.load());
            TranslationApi.registerLanguageFile(lang);
        });

    }
}
