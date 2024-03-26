package dev.crmodders.flux.mixins.rendering.shaders;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.FluxConstants;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.rendering.shaders.GameShader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.regex.Pattern;

@Mixin(GameShader.class)
public class GameShaderMixin {
    @Redirect(method = "loadShaderFile", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/GameAssetLoader;loadAsset(Ljava/lang/String;)Lcom/badlogic/gdx/files/FileHandle;"))
    FileHandle loadShaderFromMods(String fileName) {
        String noFolder = fileName.replace("shaders/","");
        if (noFolder.contains(FluxConstants.ASSET_KEY)) {
            String[] split = noFolder.split(Pattern.quote(FluxConstants.ASSET_KEY));
            if(split.length!= 2) {
                return null;
            }
            return GameAssetLoader.loadAsset(split[0] + FluxConstants.ASSET_KEY + "shader/" + split[1]);
        }
        return GameAssetLoader.loadAsset(fileName);
    }
}