package dev.crmodders.flux;

import dev.crmodders.flux.events.OnPreLoadAssetsEvent;
import dev.crmodders.flux.localization.ILanguageFile;
import dev.crmodders.flux.localization.LanguageManager;
import dev.crmodders.flux.localization.files.LanguageFileVersion1;
import net.fabricmc.api.ModInitializer;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dev.crmodders.flux.assets.FluxGameAssetLoader.LOADER;

public class FluxAPI implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("FluxAPI");

    @Override
    public void onInitialize() {
        LOGGER.info("Flux Initialized");
        FluxRegistries.EVENT_BUS.register(this);
    }

    @Subscribe
    public void onEvent(OnPreLoadAssetsEvent event) {
        ILanguageFile lang = LOADER.loadResourceSync(FluxConstants.LanguageEnUs, LanguageFileVersion1.class);
        LanguageManager.registerLanguageFile(lang);
    }

}
