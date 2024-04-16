package dev.crmodders.flux;

import com.badlogic.gdx.graphics.Color;
import dev.crmodders.flux.api.block.impl.TestBlock;
import dev.crmodders.flux.api.events.GameEvents;
import dev.crmodders.flux.api.factories.FactoryFinalizer;
import dev.crmodders.flux.api.generators.BlockModelGenerator;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxAPI implements ModInitializer {

    static Logger logger = LoggerFactory.getLogger("FluxAPI");

    @Override
    public void onInitialize() {
        logger.info("Flux Fabric Initialized");

        GameEvents.ON_REGISTER_LANGUAGE.register(() -> {
            LanguageFile lang = LanguageFile.loadLanguageFile(FluxConstants.LanguageEnUs.load());
            TranslationApi.registerLanguageFile(lang);
        });

        FluxRegistries.BLOCK_FACTORIES.add(new FactoryFinalizer<>(() -> {
            TestBlock block = new TestBlock();
            for (BlockModelGenerator generator : block.getBlockModelGenerators(Identifier.fromString("fluxapi:test_block"))) {
                generator.createColoredCuboid(0, 0, 0, 69, 69, 69, "test_model", Color.BLUE);
            }
            return block;
        }));

    }
}
