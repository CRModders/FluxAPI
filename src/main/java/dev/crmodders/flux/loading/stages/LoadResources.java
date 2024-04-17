package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.api.resource.ResourceObject;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;

public class LoadResources extends LoadStage {
    @Override
    public void initialize(GameLoader loader) {
        super.initialize(loader);
        title = new TranslationKey("fluxapi:loading_menu.registering_assets");
    }

    @Override
    public void doStage() {
        super.doStage();

        AccessableRegistry<ResourceObject> resources = FluxRegistries.GAME_RESOURCES.access();
        Identifier[] resourceIds = resources.getRegisteredNames();
        loader.setupProgressBar(loader.progress2, resourceIds.length);
        for(Identifier resourceId : resourceIds) {
            loader.incrementProgress(loader.progress2, resourceId.toString());
            ResourceObject resource = resources.get(resourceId);
            try {
                resource.handle = resource.key.load();
            } catch (Exception e) {
                GameLoader.logger.error("Error loading Resource", e);
            }
        }

    }
}
