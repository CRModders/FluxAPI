package dev.crmodders.flux.loading;

import com.badlogic.gdx.graphics.Texture;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.api.gui.ProgressBarElement;
import dev.crmodders.flux.api.gui.TextElement;
import dev.crmodders.flux.api.gui.base.BaseButton;
import dev.crmodders.flux.loading.block.BlockLoader;
import dev.crmodders.flux.loading.stages.*;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.menus.BasicMenu;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJson;
import finalforeach.cosmicreach.settings.SoundSettings;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class GameLoader extends BasicMenu implements Runnable {

    public static final String TAG = "\u001B[35;1m{Registry}\u001B[0m\u001B[37m";

    public TextElement title;
    public ProgressBarElement ram;
    public ProgressBarElement progress1;
    public ProgressBarElement progress2;
    public ProgressBarElement progress3;

    private final List<LoadStage> stages = new ArrayList<>();

    private CountDownLatch lock;
    private Thread loadingThread;

    public BlockLoader blockLoader;

    @Override
    public void create() {
        super.create();

        title = new TextElement(new TranslationKey("fluxapi:loading_menu.waiting_title"));
        title.setPosition(0, -100);
        title.automaticSize = true;
        title.automaticSizePadding = 4f;
        title.fontSize = 42;
        title.backgroundEnabled = false;
        addFluxElement(title);

        ram = new ProgressBarElement();
        ram.setBounds(0, -250, 500, 40);
        ram.prefixTranslation = false;
        ram.translation = new TranslationKey("fluxapi:loading_menu.ram_usage");
        addFluxElement(ram);

        progress1 = new ProgressBarElement();
        progress1.setBounds(0, -40, 500, 40);
        progress1.hideIfZero = true;
        addFluxElement(progress1);

        progress2 = new ProgressBarElement();
        progress2.setBounds(0, 20, 500, 40);
        progress2.hideIfZero = true;
        addFluxElement(progress2);

        progress3 = new ProgressBarElement();
        progress3.setBounds(0, 80, 500, 40);
        progress3.hideIfZero = true;
        addFluxElement(progress3);

        blockLoader = new BlockLoader();
        GameSingletons.blockModelInstantiator = blockLoader.instantiator;

        stages.add(new InitializingCosmicReach());

        for(LoadStage stage : stages) {
            stage.initialize(this);
        }

        loadingThread = new Thread(this, "GameLoader");
        loadingThread.start();
    }

    @Override
    public void run() {
        progress1.range = stages.size();
        for(LoadStage stage : stages) {
            progress1.translation = stage.title;
            progress1.value++;
            progress1.updateText();

            stage.doStage();
        }
        FluxConstants.FluxHasLoaded = true;
    }

    @Override
    public void render(float partTime) {

        Runtime runtime = Runtime.getRuntime();
        ram.value = (int) ((runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
        ram.range = (int) (runtime.maxMemory() / (1024 * 1024));
        ram.updateText();

        if(FluxConstants.FluxHasLoaded) {

            blockLoader.loadModels();
            blockLoader.hookBlockManager();

            GameState.switchToGameState(FluxConstants.MAIN_MENU);
        }

        super.render(partTime);
    }

    public void setupProgressBar(ProgressBarElement bar, int range) {
        bar.translation = null;
        bar.prefix = null;
        bar.range = range;
        bar.value = 0;
        bar.updateText();
    }

    public void setupProgressBar(ProgressBarElement bar, int range, TranslationKey text) {
        bar.translation = text;
        bar.prefix = null;
        bar.range = range;
        bar.value = 0;
        bar.updateText();
    }

    public void setupProgressBar(ProgressBarElement bar, int range, String text) {
        bar.translation = null;
        bar.prefix = text;
        bar.range = range;
        bar.value = 0;
        bar.updateText();
    }

    public void incrementProgress(ProgressBarElement bar) {
        bar.value++;
        bar.updateText();
    }

    public void incrementProgress(ProgressBarElement bar, String text) {
        bar.value++;
        bar.prefix = text;
        bar.updateText();
    }

}
