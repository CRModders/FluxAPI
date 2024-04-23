package dev.crmodders.flux.engine.blocks.models.vertex;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import dev.crmodders.flux.annotations.Experimental;
import dev.crmodders.flux.engine.blocks.CustomTextureLoader;
import dev.crmodders.flux.mesh.IVertexData;
import finalforeach.cosmicreach.RuntimeInfo;
import finalforeach.cosmicreach.rendering.IMeshData;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModel;

@Experimental
public class BlockModelVertex extends BlockModel implements IVertexData {

    public String modelName;
    public FloatArray vertices;

    public BlockModelVertex() {
        isPosXFaceOccluding = false;
        isNegXFaceOccluding = false;
        isPosYFaceOccluding = false;
        isNegYFaceOccluding = false;
        isPosZFaceOccluding = false;
        isNegZFaceOccluding = false;
        isPosXFacePartOccluding = true;
        isNegXFacePartOccluding = true;
        isPosYFacePartOccluding = true;
        isNegYFacePartOccluding = true;
        isPosZFacePartOccluding = true;
        isNegZFacePartOccluding = true;
    }

    public void initialize() {

    }

    @Override
    public void addVertex(float x, float y, float z, float u, float v, float r, float g, float b, float a) {
        vertices.add(x);
        vertices.add(y);
        vertices.add(z);
        float color = Color.toFloatBits(r, g, b, a);
        vertices.add(color);
        if(RuntimeInfo.isMac) {
            vertices.add(u);
            vertices.add(v);
        } else {
            float uvIndex = CustomTextureLoader.createUBOFloatsIdx(u, v);
            vertices.add(uvIndex);
        }
    }

    @Override
    public void addVertices(IMeshData meshData, int bx, int by, int bz, int opaqueBitmask, short[] blockLightLevels, int[] skyLightLevels) {

    }

    @Override
    public boolean isGreedyCube() {
        return false;
    }

    @Override
    public boolean canGreedyCombine() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void getAllBoundingBoxes(Array<BoundingBox> array, int i, int i1, int i2) {

    }

}
