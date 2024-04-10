package dev.crmodders.flux.loading.stages;

import dev.crmodders.flux.api.gui.ProgressBarElement;
import dev.crmodders.flux.api.resource.ResourceObject;
import dev.crmodders.flux.loading.GameLoader;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.loading.TaskBatch;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.logging.LogWrapper;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameAssetLoader;

import java.util.concurrent.ExecutorService;

public class RegisterAssets implements LoadStage {
    @Override
    public TranslationKey title() {
        return new TranslationKey("fluxapi:loading_menu.registering_assets");
    }

    @Override
    public void doStage(ProgressBarElement progress, ExecutorService threadPool, ExecutorService glThread) {
        AccessableRegistry<ResourceObject> registryAccess = (AccessableRegistry<ResourceObject>) FluxRegistries.GAME_RESOURCES;
        progress.range = registryAccess.getRegisteredNames().length;

        TaskBatch batch = new TaskBatch(threadPool);
        for (Identifier resourceId : registryAccess.getRegisteredNames()) {
            ResourceObject resource = registryAccess.get(resourceId);
            batch.submit(() -> {
                if (resource.handle == null)
                    resource.handle = GameAssetLoader.loadAsset(resource.key.toString());
                progress.value++;
                LogWrapper.info("%s: Registered Asset: %s".formatted(GameLoader.TAG, resourceId));
            });
        }
        batch.await(glThread);
    }

}
