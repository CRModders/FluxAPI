package dev.crmodders.flux.loading.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.api.gui.ProgressBarElement;
import dev.crmodders.flux.loading.LoadStage;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.io.SaveLocation;
import org.pmw.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CosmicReachLoader implements LoadStage {
    @Override
    public TranslationKey title() {
        return new TranslationKey("fluxapi:loading_menu.loading_cosmic_reach");
    }

    @Override
    public void doStage(ProgressBarElement progress, ExecutorService threadPool, ExecutorService glThread) {
        List<String> blockNames = new ArrayList<>();

        String[] defaultAssetList = Gdx.files.internal("assets.txt").readString().split("\n");
        for(String asset : defaultAssetList) {
            try {
                if (asset.startsWith("blocks/") && asset.endsWith(".json") && Gdx.files.internal(asset).exists()) {
                    String fileName = asset.replace("blocks/", "");
                    blockNames.add(fileName.replace(".json", ""));
                }
            } catch (Exception e) {
                Logger.error(e);
            }
        }

        FileHandle[] moddedBlockDir = Gdx.files.absolute(SaveLocation.getSaveFolderLocation() + "/mods/assets/blocks").list();
        for(FileHandle modded : moddedBlockDir) {
            if (modded.name().endsWith(".json")) {
                blockNames.add(modded.nameWithoutExtension());
            }
        }

        glThread.submit(() -> {
            progress.translation = new TranslationKey("fluxapi:loading_menu.loading_blocks");
            progress.range = blockNames.size();
        });
        for(String blockName : blockNames) {
            glThread.submit(() -> {
                progress.value++;
                Block.getInstance(blockName);
            });
        }

    }
}
