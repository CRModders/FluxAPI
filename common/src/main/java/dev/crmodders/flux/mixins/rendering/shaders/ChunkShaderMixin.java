package dev.crmodders.flux.mixins.rendering.shaders;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.FluxConstants;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.rendering.shaders.ChunkShader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.regex.Pattern;

@Mixin(ChunkShader.class)
public class ChunkShaderMixin {
    @Redirect(method = "addToAllBlocksTexture",at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/GameAssetLoader;loadAsset(Ljava/lang/String;)Lcom/badlogic/gdx/files/FileHandle;"))
    private static FileHandle loadModdedBlockTextures(String fileName){
        String noFolder = fileName.replace("textures/blocks/","");
        if (noFolder.contains(":")) {
            String[] split = noFolder.split(Pattern.quote(":"));
            if(split.length!= 2) {
                return null;
            }
            return GameAssetLoader.loadAsset(split[0] + FluxConstants.ASSET_KEY + "textures/blocks/" + split[1]);
        }
        return GameAssetLoader.loadAsset(fileName);
    }
}