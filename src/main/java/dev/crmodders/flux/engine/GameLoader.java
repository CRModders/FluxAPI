package dev.crmodders.flux.engine;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxRegistries;
import dev.crmodders.flux.engine.blocks.BlockLoader;
import dev.crmodders.flux.engine.stages.Initialize;
import dev.crmodders.flux.engine.stages.LoadingCosmicReach;
import dev.crmodders.flux.engine.stages.PostInitialize;
import dev.crmodders.flux.engine.stages.PreInitialize;
import dev.crmodders.flux.events.OnRegisterLanguageEvent;
import dev.crmodders.flux.gui.ProgressBarElement;
import dev.crmodders.flux.gui.TextElement;
import dev.crmodders.flux.localization.LanguageManager;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.localization.TranslationLocale;
import dev.crmodders.flux.menus.BasicMenu;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.PrealphaPreamble;
import finalforeach.cosmicreach.settings.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class GameLoader extends BasicMenu implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger("FluxAPI / GameLoader");

    public TextElement title;
    public ProgressBarElement ram;
    public ProgressBarElement progress1;
    public ProgressBarElement progress2;
    public ProgressBarElement progress3;

    private final Queue<LoadStage> stages = new LinkedList<>();

    private Queue<Runnable> glQueue;

    public BlockLoader blockLoader;

    @Override
    public void create() {
        super.create();

        registerLanguages();

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
        GameSingletons.blockModelInstantiator = blockLoader.factory;

        addStage(new PreInitialize());
        addStage(new Initialize());
        addStage(new LoadingCosmicReach());
        addStage(new PostInitialize());

        glQueue = new LinkedList<>();

        Thread loadingThread = new Thread(this, "GameLoader");
        loadingThread.start();
    }

    private void registerLanguages() {
        // fire lang event, select english, select preferred language if existing
        FluxRegistries.EVENT_BUS.post(new OnRegisterLanguageEvent());
        LanguageManager.selectLanguage(TranslationLocale.fromLanguageTag("en-US"));
        String chosen = Preferences.chosenLang.getValue();
        if(chosen != null) LanguageManager.selectLanguage(TranslationLocale.fromLanguageTag(chosen));
    }

    public void addStage(LoadStage stage) {
        stages.add(stage);
        stage.initialize(this);
    }

    @Override
    public void run() {
        progress1.range = stages.size();
        while(!stages.isEmpty()) {
            LoadStage stage = stages.poll();
            progress1.translation = stage.title;
            progress1.value++;
            progress1.updateText();

            stage.doStage();

            logger.info("Manually Garbage Collecting");
            System.gc();

            CountDownLatch glLock = new CountDownLatch(1);
            List<Runnable> glTasks = stage.getGlTasks();
            glQueue.addAll(glTasks);
            glQueue.add( glLock::countDown );

            try {
                glLock.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        FluxConstants.FluxHasLoaded = true;
    }

    @Override
    public void render(float partTime) {

        Runtime runtime = Runtime.getRuntime();
        ram.value = (int) ((runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
        ram.range = (int) (runtime.maxMemory() / (1024 * 1024));
        ram.updateText();

        long endTime = System.currentTimeMillis() + 50;
        while (!glQueue.isEmpty() && System.currentTimeMillis() < endTime) {
            Runnable glTask = glQueue.poll();
            glTask.run();
        }

        if(FluxConstants.FluxHasLoaded) {
            GameState.switchToGameState(new PrealphaPreamble());
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
