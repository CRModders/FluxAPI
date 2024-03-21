package io.github.crmodders.flux.mixin.api;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.io.SaveLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

@Mixin(GameAssetLoader.class)
public class GameAssetLoaderMixin {

    @Shadow @Final public static HashMap<String, FileHandle> ALL_ASSETS;

    @Inject(method = "loadAsset(Ljava/lang/String;Z)Lcom/badlogic/gdx/files/FileHandle;", at = @At("HEAD"), cancellable = true)
    private static void loadAsset(String fileName, boolean forceReload, CallbackInfoReturnable<FileHandle> cir) {
        if (!forceReload && ALL_ASSETS.containsKey(fileName)) {
            cir.setReturnValue(ALL_ASSETS.get(fileName));
        } else {
            System.out.print("Loading " + fileName);
            Files var10000 = Gdx.files;
            String var10001 = SaveLocation.getSaveFolderLocation();
            FileHandle moddedFile = var10000.absolute(var10001 + "/mods/assets/" + fileName);
            if (moddedFile.exists()) {
                System.out.println(" from mods folder");
                ALL_ASSETS.put(fileName, moddedFile);
                cir.setReturnValue(moddedFile);
            } else {
                System.out.println(" from jar");
                FileHandle fileFromJar = Gdx.files.internal(fileName);
                ALL_ASSETS.put(fileName, fileFromJar);
                cir.setReturnValue(fileFromJar);
            }
        }
    }

}
