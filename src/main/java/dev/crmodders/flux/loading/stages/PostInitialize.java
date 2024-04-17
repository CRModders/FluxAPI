package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;

public class PostInitialize extends LoadStage {
    @Override
    public void initialize(GameLoader loader) {
        super.initialize(loader);
        title = new TranslationKey("fluxapi:loading_menu.initializing");
    }

    @Override
    public void doStage() {
        super.doStage();

        AccessableRegistry<Runnable> mods = FluxRegistries.ON_POST_INITIALIZE.access();
        Identifier[] modIds = mods.getRegisteredNames();
        loader.setupProgressBar(loader.progress2, modIds.length, "Initializing Mods: PostInit");
        for(Identifier modId : modIds) {
            loader.incrementProgress(loader.progress2, modId.namespace);
            Runnable runnable = mods.get(modId);
            runnable.run();
        }

    }
}