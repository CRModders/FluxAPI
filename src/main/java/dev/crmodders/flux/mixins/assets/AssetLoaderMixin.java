package dev.crmodders.flux.mixins.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.logger.LogWrapper;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.io.SaveLocation;
import org.pmw.tinylog.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;

@Mixin(GameAssetLoader.class)
public class AssetLoaderMixin {
    private static String TAG = "\u001B[35;1m{Assets}\u001B[0m\u001B[37m:";

    @Shadow @Final public static HashMap<String, FileHandle> ALL_ASSETS;

    /**
     * @author written by replet, rewritten by Mr Zombii
     * @reason Improves asset loading
     **/
    @Overwrite
    public static FileHandle loadAsset(String fileName, boolean forceReload) {
        Identifier location = Identifier.fromString(fileName);
        if (!forceReload && ALL_ASSETS.containsKey(location.toString())) return ALL_ASSETS.get(location.toString());

        FileHandle modLocationFile = Gdx.files.absolute(SaveLocation.getSaveFolderLocation() + "/mods/assets/" + fileName);
        if (modLocationFile.exists()) {
            LogWrapper.info("%s Loading %s from DataMods".formatted(TAG, fileName));
            ALL_ASSETS.put(fileName, modLocationFile);
            return modLocationFile;
        }

        FileHandle vanillaLocationFile = Gdx.files.internal(location.name);
        if (vanillaLocationFile.exists()) {
            LogWrapper.info("%s Loading %s from Cosmic Reach".formatted(TAG, fileName));
            ALL_ASSETS.put(fileName, modLocationFile);
            return vanillaLocationFile;
        }

        FileHandle classpathLocationFile = Gdx.files.classpath("assets/%s/%s".formatted(location.namespace, location.name));
        if (classpathLocationFile.exists()) {
            LogWrapper.info("%s Loading %s from the Classpath".formatted(TAG, fileName));
            ALL_ASSETS.put(fileName, modLocationFile);
            return classpathLocationFile;
        }

        Logger.error("%s Cannot Load %s from Classpath, CosmicReach, or DataMods".formatted(TAG, fileName));
        return null;
    }

    @Redirect(method = "getSound", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/GameAssetLoader;loadAsset(Ljava/lang/String;)Lcom/badlogic/gdx/files/FileHandle;"))
    private static FileHandle getSound(String fileName) {
        String noFolder = fileName.replace("sounds/blocks/","");
        if (noFolder.contains(":")) {
            Identifier id = Identifier.fromString(noFolder);
            id.name = "sounds/blocks/" + id.name;
            return GameAssetLoader.loadAsset(id.toString());
        }
        return GameAssetLoader.loadAsset(fileName);
    }

}