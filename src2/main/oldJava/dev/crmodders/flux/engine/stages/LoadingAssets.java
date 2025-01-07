package dev.crmodders.flux.engine.stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import de.pottgames.tuningfork.SoundBuffer;
import dev.crmodders.flux.assets.VanillaAssetLocations;
import dev.crmodders.flux.engine.GameLoader;
import dev.crmodders.flux.engine.LoadStage;
import dev.crmodders.flux.events.OnLoadAssetsEvent;
import dev.crmodders.flux.events.OnLoadAssetsFinishedEvent;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.tags.ResourceLocation;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static dev.crmodders.flux.FluxRegistries.EVENT_BUS;
import static dev.crmodders.flux.assets.FluxGameAssetLoader.LOADER;

public class LoadingAssets extends LoadStage {

    private static final TranslationKey TEXT_TITLE = new TranslationKey("fluxapi:loading_menu.registering_assets");
    private static final TranslationKey TEXT_LOADING_ASSETS = new TranslationKey("fluxapi:loading_menu.loading_assets");

    @Override
    public void initialize(GameLoader loader) {
        super.initialize(loader);
        title = TEXT_TITLE;
    }

    @Subscribe
    public void onEvent(OnLoadAssetsEvent event) {
        List<ResourceLocation> textures = new ArrayList<>();
        textures.addAll(VanillaAssetLocations.getInternalFiles("textures/ui", ".png"));
        textures.addAll(VanillaAssetLocations.getInternalFiles("textures/items", ".png"));
        textures.addAll(VanillaAssetLocations.getInternalFiles("textures/entities", ".png"));
        textures.addAll(VanillaAssetLocations.getInternalFiles("lang/textures/", ".png"));
        textures.forEach( location -> LOADER.loadResource(location, Texture.class) );

        List<ResourceLocation> sounds = new ArrayList<>();
        sounds.addAll(VanillaAssetLocations.getInternalFiles("sounds/", ".ogg"));
        sounds.addAll(VanillaAssetLocations.getVanillaModFiles("sounds/", ".ogg"));
        sounds.forEach( location -> LOADER.loadResource(location, SoundBuffer.class) );
    }

    @Override
    public void doStage() {
        super.doStage();
        EVENT_BUS.post(new OnLoadAssetsEvent());
    }

    @Override
    public List<Runnable> getGlTasks() {
        List<Runnable> tasks = super.getGlTasks();
        AssetManager manager = LOADER.getAssetManager();
        tasks.add( () -> loader.setupProgressBar(loader.progressBar2, manager.getQueuedAssets(), TEXT_LOADING_ASSETS) );
        for(int i = 0; i < manager.getQueuedAssets(); i++) {
            tasks.add( () -> loader.incrementProgress(loader.progressBar2) );
            tasks.add(manager::update);
        }
        // let's be safe
        tasks.add(manager::finishLoading);
        tasks.add( () -> EVENT_BUS.post(new OnLoadAssetsFinishedEvent()) );
        return tasks;
    }

}
