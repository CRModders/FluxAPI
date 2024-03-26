package dev.crmodders.flux.mixins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.FluxConstants;
import finalforeach.cosmicreach.rendering.shaders.GameShader;
import org.pmw.tinylog.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.regex.Pattern;

@Mixin(finalforeach.cosmicreach.GameAssetLoader.class)
public class AssetLoaderMixin {
    private static String TAG = "\u001B[35;1m{Assets}\u001B[0m\u001B[37m:";

    @Shadow @Final public static HashMap<String, FileHandle> ALL_ASSETS;

    /**
     * @author replet
     * @reason So mods can load assets
     **/
    @Inject(method = "loadAsset(Ljava/lang/String;Z)Lcom/badlogic/gdx/files/FileHandle;",at= @At(value = "INVOKE_ASSIGN", target = "Lcom/badlogic/gdx/Files;absolute(Ljava/lang/String;)Lcom/badlogic/gdx/files/FileHandle;",shift = At.Shift.BEFORE),cancellable = true)
    private static void file(String fileName, boolean forceReload, CallbackInfoReturnable<FileHandle> cir) {
        String[] arr = null;

        if (fileName.contains(FluxConstants.ASSET_KEY)) {
            arr = fileName.split(Pattern.quote(FluxConstants.ASSET_KEY));
        }

        if (fileName.contains(FluxConstants.ASSET_KEY)) {
            Logger.info("%s Loading %s using Flux".formatted(TAG, fileName));
        } else {
            if (fileName.startsWith("mods/assets"))
                Logger.info("%s Loading %s from DataMods".formatted(TAG, fileName));
            else
                Logger.info("%s Loading %s from Jar".formatted(TAG, fileName));
        }
        if (arr != null) {
            if (arr.length==2) {
                if (arr[0].equalsIgnoreCase("base")) {
                    if (!forceReload && ALL_ASSETS.containsKey(arr[1])) {
                        cir.setReturnValue(ALL_ASSETS.get(arr[1]));
                    }
                    FileHandle file = Gdx.files.classpath(arr[1]);
                    ALL_ASSETS.put(fileName, file);
                    cir.setReturnValue(file);
                }

                FileHandle file = Gdx.files.classpath("assets/" + arr[0] + "/" + arr[1]);
                if (file.exists()) {
                    Logger.info("%s Loading %s from FabricMod".formatted(TAG, fileName));
                    ALL_ASSETS.put(fileName, file);
                    cir.setReturnValue(file);
                }
            }
        }
    }

    @Redirect(method = "loadAsset(Ljava/lang/String;Z)Lcom/badlogic/gdx/files/FileHandle;", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;print(Ljava/lang/String;)V"))
    private static void printCapture(PrintStream instance, String s) {
        String a = s.replace("Loading ","");

        if (s.contains(FluxConstants.ASSET_KEY)) {
            String b = a.replace(FluxConstants.ASSET_KEY,":");
            s = "Loading " + b;
        } else {
            if (s.startsWith("mods/assets")) {
                s = "Loading " + "DataMods:" + a;
            } else {
                s = "Loading " + "base:" + a;
            }
        }
//        instance.print(s);
    }

    @Redirect(method = "loadAsset(Ljava/lang/String;Z)Lcom/badlogic/gdx/files/FileHandle;", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V"))
    private static void printCapture2(PrintStream instance, String s) {
        String a = s.replace("Loading ","");

        if (s.contains(FluxConstants.ASSET_KEY)) {
            String b = a.replace(FluxConstants.ASSET_KEY,":");
            s = "Loading " + b;
        } else {
            if (s.startsWith("mods/assets")) {
                s = "Loading " + "DataMods:" + a;
            } else {
                s = "Loading " + "base:" + a;
            }
        }
//        instance.print(s);
    }
}