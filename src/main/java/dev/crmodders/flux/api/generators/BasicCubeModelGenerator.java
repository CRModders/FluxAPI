package dev.crmodders.flux.api.generators;

import com.badlogic.gdx.graphics.Pixmap;
import dev.crmodders.flux.tags.Identifier;

public class BasicCubeModelGenerator extends  BlockModelGenerator {
    public BasicCubeModelGenerator(Identifier modelName, Pixmap top, Pixmap bottom, Pixmap side) {
        super(modelName);
        createTexture("top", top);
        createTexture("bottom", bottom);
        createTexture("side", side);
        Cuboid cuboid = createCuboid(0, 0, 0, 16, 16, 16);
        Cuboid.Face topFace = cuboid.faces[Cuboid.LOCAL_POS_Y];
        topFace.texture = getModelTextureName("top");
        Cuboid.Face bottomFace = cuboid.faces[Cuboid.LOCAL_NEG_Y];
        bottomFace.texture = getModelTextureName("bottom");
        Cuboid.Face sideFace1 = cuboid.faces[Cuboid.LOCAL_POS_X];
        sideFace1.texture = getModelTextureName("side");
        Cuboid.Face sideFace2 = cuboid.faces[Cuboid.LOCAL_NEG_X];
        sideFace2.texture = getModelTextureName("side");
        Cuboid.Face sideFace3 = cuboid.faces[Cuboid.LOCAL_POS_Z];
        sideFace3.texture = getModelTextureName("side");
        Cuboid.Face sideFace4 = cuboid.faces[Cuboid.LOCAL_NEG_Z];
        sideFace4.texture = getModelTextureName("side");
    }

}
