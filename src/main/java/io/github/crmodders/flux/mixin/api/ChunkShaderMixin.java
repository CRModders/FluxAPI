package io.github.crmodders.flux.mixin.api;

import com.badlogic.gdx.graphics.*;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.rendering.shaders.ChunkShader;
import io.github.crmodders.flux.FluxAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// UNFINISHED
@Mixin(ChunkShader.class)
@SuppressWarnings("unchecked")
public class ChunkShaderMixin {

    @Shadow public static int allBlocksTexSize = 256;
    @Shadow private static Pixmap allBlocksPix;
    @Shadow private static int terrainPixCurX = 0;

    @Shadow private static int terrainPixCurY = 0;

    @Shadow private static HashMap<String, float[]> storedUVs;

    private static Object getPrivField(Class<?> clazz, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(clazz);
    }

    private static void setPrivField(Class<?> clazz, String fieldName, Object data) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(clazz, data);
    }

//    @Inject(method = "addToAllBlocksTexture", at = @At("HEAD"), cancellable = true)
/**
 * @author Zombii
 * @reason to upgrade texture sizes
 */
    @Overwrite
    public static float[] addToAllBlocksTexture(String blockTextureName) throws NoSuchFieldException, IllegalAccessException {
//        Pixmap allBlocksPix = (Pixmap) getPrivField(ChunkShader.class, "allBlocksPix");
//        if (allBlocksPix.getWidth() == 256) {
//            setPrivField(ChunkShader.class, "allBlocksPix", new Pixmap(1024, 512, Pixmap.Format.RGBA8888));
//        }


        float[] storedUV = storedUVs.get(blockTextureName);
        if (storedUV != null) {
            return storedUV;
        } else {
            Texture blockTex = new Texture(GameAssetLoader.loadAsset("textures/blocks/" + blockTextureName));
            TextureData texData = blockTex.getTextureData();
            texData.prepare();
            Pixmap blockPix = texData.consumePixmap();
            float[] uv = new float[]{
                    (float)(
                            terrainPixCurX / blockPix.getWidth()
                    ),
                    (float)(
                            terrainPixCurY / blockPix.getHeight()
                    )
            };
            allBlocksPix.drawPixmap(blockPix, terrainPixCurX, terrainPixCurY);
            terrainPixCurX += blockPix.getWidth();
            if ((float)terrainPixCurX > ((allBlocksPix.getWidth() * 14 / 16f))) {
                terrainPixCurX = 0;
                terrainPixCurY += blockPix.getHeight();
            }

            if (texData.disposePixmap()) {
                blockPix.dispose();
            }

            blockTex.dispose();
            if (ChunkShader.chunkTerrainTex != null) {
                ChunkShader.chunkTerrainTex.dispose();
                ChunkShader.chunkTerrainTex = null;
            }

            storedUVs.put(blockTextureName, uv);
            return uv;
        }
    }
}
