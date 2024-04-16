package dev.crmodders.flux.loading.block;

import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import dev.crmodders.flux.annotations.Experimental;
import finalforeach.cosmicreach.rendering.IMeshData;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModel;

@Experimental
public class BlockModelVertex extends BlockModel {

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
