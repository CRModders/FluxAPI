package dev.crmodders.flux.loading;

import com.badlogic.gdx.graphics.Texture;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.api.v5.gui.ProgressBarElement;
import dev.crmodders.flux.api.v5.gui.TextElement;
import dev.crmodders.flux.api.v5.gui.base.BaseButton;
import dev.crmodders.flux.loading.stages.*;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.menus.BasicMenu;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.settings.SoundSettings;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameLoader extends BasicMenu {

    public static final String TAG = "\u001B[35;1m{Registry}\u001B[0m\u001B[37m";

    public ProgressBarElement ram;
    public ProgressBarElement major;
    public ProgressBarElement minor;
    public BaseButton icon;

    private final Queue<LoadStage> stages = new LinkedList<>();

    private CurrentThreadExecutor glThread;
    private ExecutorService threadPool;

    private int delay;

    @Override
    public void create() {
        super.create();
        TextElement element = new TextElement(new TranslationKey("fluxapi:loading_menu.waiting_title"));
        element.setPosition(0, -100);
        element.automaticSize = true;
        element.automaticSizePadding = 4f;
        element.fontSize = 42;
        element.backgroundEnabled = false;
        addFluxElement(element);

        ram = new ProgressBarElement();
        ram.setBounds(0, -250, 500, 40);
        ram.prefixTranslation = false;
        ram.translation = new TranslationKey("fluxapi:loading_menu.ram_usage");
        addFluxElement(ram);

        major = new ProgressBarElement();
        major.setBounds(0, -40, 500, 40);
        major.hideIfZero = true;
        addFluxElement(major);

        minor = new ProgressBarElement();
        minor.setBounds(0, 20, 500, 40);
        minor.hideIfZero = true;
        addFluxElement(minor);

        icon = new BaseButton();
        icon.setBounds(5, 5, 100, 100);
        icon.setAnchors(HorizontalAnchor.RIGHT_ALIGNED, VerticalAnchor.BOTTOM_ALIGNED);
        icon.image = new Texture(FluxConstants.CRModdersIcon.load());
        icon.iconSize = 75;
        icon.iconPadding = 0;
        icon.backgroundEnabled = false;
        addFluxElement(icon);

        glThread = new CurrentThreadExecutor();
        threadPool = Executors.newFixedThreadPool(4);

        addStage(new PreInitialize());
        addStage(new CosmicReachLoader());
        addStage(new Initialize());
        addStage(new RegisterAssets());
        addStage(new RegisterBlocks());
        addStage(new RegisterBlockActions());
        addStage(new RegisterBlockEvents());
        addStage(new FinalizeBlocks());
        addStage(new PostInitialize());

        major.range = stages.size();
        glThread.submit(this::nextStage);

        delay = 10;
    }

    @Override
    public void render(float partTime) {
        if(delay > 0) {
            delay--;
        }

        // run gl tasks for 100ms max
        if(delay <= 0) glThread.run(100, TimeUnit.MILLISECONDS);

        Runtime runtime = Runtime.getRuntime();
        ram.value = (int) ((runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
        ram.range = (int) (runtime.maxMemory() / (1024 * 1024));
        icon.iconRotation += 1.5f;

        ram.updateText();
        major.updateText();
        minor.updateText();
        icon.repaint();

        if(FluxConstants.FluxHasLoaded) {
            if(SoundSettings.isSoundEnabled()) {
                UIElement.onClickSound.setPitch(UIElement.onClickSound.play(), 4f);
            }
            GameState.switchToGameState(FluxConstants.MAIN_MENU);
        }

        super.render(partTime);
    }

    protected void addStage(LoadStage stage) {
        stages.add(stage);
    }

    protected void nextStage() {
        if(stages.isEmpty()) {
            glThread.submit(() -> FluxConstants.FluxHasLoaded = true);
            return;
        }
        LoadStage stage = stages.poll();
        glThread.submit(() -> {
            major.translation = stage.title();
            major.value++;
            minor.translation = null;
            minor.range = 0;
            minor.value = 0;
        });
        stage.doStage(minor, threadPool, glThread);
        glThread.submit(this::nextStage);
    }

}
