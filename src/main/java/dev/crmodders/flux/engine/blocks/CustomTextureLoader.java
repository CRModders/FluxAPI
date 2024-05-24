package dev.crmodders.flux.engine.blocks;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import dev.crmodders.flux.mixins.blocks.ChunkShaderAccessor;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJsonTexture;
import finalforeach.cosmicreach.rendering.shaders.ChunkShader;

import java.util.HashMap;

public class CustomTextureLoader {

    /**
     * Updates the block texture atlas and puts the texture in the global
     * texture cache, so the regular ChunkShader methods don't try to load
     * the texture
     * @param textureName name of the texture, these are global be warned
*    *                    about name collision
     * @param blockTex a pixmap representing your texture, this has to follow the guidelines
     *                 from data modding, width and height have to be equal
     */
    public static void registerTexture(String textureName, Pixmap blockTex) {
        if (blockTex.getWidth() != blockTex.getHeight()) {
            throw new RuntimeException("Width and height of " + textureName + " must be the same!");
        } else {
            int terrainPixCurX = ChunkShaderAccessor.getTerrainPixCurX();
            int terrainPixCurY = ChunkShaderAccessor.getTerrainPixCurY();
            Pixmap allBlocksPix = ChunkShaderAccessor.getAllBlocksPix();
            Texture chunkTerrainTex = ChunkShaderAccessor.getChunkTerrainTex();
            HashMap<String, BlockModelJsonTexture> storedTexs = ChunkShaderAccessor.getStoredTexs();

            float[] uv = new float[]{(float)(terrainPixCurX / blockTex.getWidth()), (float)(terrainPixCurY / blockTex.getHeight())};
            allBlocksPix.drawPixmap(blockTex, terrainPixCurX, terrainPixCurY);
            terrainPixCurX += blockTex.getWidth();
            if ((float)terrainPixCurX > (float)(allBlocksPix.getWidth() * 15) / 16.0F) {
                terrainPixCurX = 0;
                terrainPixCurY += blockTex.getHeight();
            }

            if (chunkTerrainTex != null) {
                chunkTerrainTex.dispose();
                chunkTerrainTex = null;
            }

            ChunkShaderAccessor.setTerrainPixCurX(terrainPixCurX);
            ChunkShaderAccessor.setTerrainPixCurY(terrainPixCurY);
            ChunkShaderAccessor.setChunkTerrainTex(chunkTerrainTex);

            BlockModelJsonTexture t = new BlockModelJsonTexture();
            t.fileName = textureName;
            t.uv = uv;
            storedTexs.put(textureName, t);
        }
    }

    public static int createUBOFloatsIdx(final float u, final float v) {
        for (int i = 0; i < ChunkShader.faceTexBufFloats.size; i += 2) {
            if (ChunkShader.faceTexBufFloats.get(i) == u && ChunkShader.faceTexBufFloats.get(i + 1) == v) {
                return i / 2;
            }
        }
        final int fIdx = ChunkShader.faceTexBufFloats.size / 2;
        ChunkShader.faceTexBufFloats.add(u);
        ChunkShader.faceTexBufFloats.add(v);
        return fIdx;
    }

}
