package dev.crmodders.flux.mixins.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.io.SaveLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;

@Mixin(GameAssetLoader.class)
public class AssetLoaderMixin {
    private static Logger logger = LoggerFactory.getLogger("FluxAPI / AssetLoader");

    @Shadow @Final public static HashMap<String, FileHandle> ALL_ASSETS;

    /**
     * @author written by replet, rewritten by Mr Zombii
     * @reason Improves asset loading
     **/
    @Overwrite
    public static FileHandle loadAsset(String fileName, boolean forceReload) {
        Identifier location = Identifier.fromString(fileName);
        if (!forceReload && ALL_ASSETS.containsKey(location.toString()))
            return ALL_ASSETS.get(location.toString());

        if("base".equals(location.namespace)) {
            fileName = location.name;
        }

        FileHandle classpathLocationFile = Gdx.files.classpath("assets/%s/%s".formatted(location.namespace, location.name));
        if (classpathLocationFile.exists()) {
            logger.info("Loading {} from the Classpath", fileName);
            ALL_ASSETS.put(fileName, classpathLocationFile);
            return classpathLocationFile;
        }

        FileHandle modLocationFile = Gdx.files.absolute(SaveLocation.getSaveFolderLocation() + "/mods/assets/" + fileName);
        if (modLocationFile.exists()) {
            logger.info("Loading {} from DataMods", fileName);
            ALL_ASSETS.put(fileName, modLocationFile);
            return modLocationFile;
        }

        FileHandle vanillaLocationFile = Gdx.files.internal(location.name);
        if (vanillaLocationFile.exists()) {
            logger.info("Loading {} from Cosmic Reach Jar", fileName);
            ALL_ASSETS.put(fileName, vanillaLocationFile);
            return vanillaLocationFile;
        }

        logger.error("Cannot find the resource {}, ResourceId: {}", fileName, location);
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