package dev.crmodders.flux.mixins.blocks;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJsonTexture;
import finalforeach.cosmicreach.rendering.shaders.ChunkShader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;

@Mixin(ChunkShader.class)
public interface ChunkShaderAccessor {

    @Accessor static int getTerrainPixCurX() { throw new IllegalStateException(); }
    @Accessor static void setTerrainPixCurX(int x) { throw new IllegalStateException(); }
    @Accessor static int getTerrainPixCurY() { throw new IllegalStateException(); }
    @Accessor static void setTerrainPixCurY(int y) { throw new IllegalStateException(); }
    @Accessor static Pixmap getAllBlocksPix() { throw new IllegalStateException(); }
    @Accessor static Texture getChunkTerrainTex() { throw new IllegalStateException(); }
    @Accessor static void setChunkTerrainTex(Texture tex) { throw new IllegalStateException(); }
    @Accessor static HashMap<String, BlockModelJsonTexture> getStoredTexs() { throw new IllegalStateException(); }
}
