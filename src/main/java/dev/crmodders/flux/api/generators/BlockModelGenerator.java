package dev.crmodders.flux.api.generators;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.OrderedMap;
import dev.crmodders.flux.loading.block.BlockLoader;
import dev.crmodders.flux.loading.block.BlockModelFlux;
import dev.crmodders.flux.loading.block.BlockModelFluxCuboid;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJsonTexture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockModelGenerator implements IGenerator {

    public static class Cuboid {

        public static final int LOCAL_NEG_X = 0;
        public static final int LOCAL_POS_X = 1;
        public static final int LOCAL_NEG_Y = 2;
        public static final int LOCAL_POS_Y = 3;
        public static final int LOCAL_NEG_Z = 4;
        public static final int LOCAL_POS_Z = 5;


        public static class Face {
            public String id = "";

            public String texture = "all";
            public int u1 = 0, v1 = 0, u2 = 16, v2 = 16;
            public int uvRotation = 0;
            public boolean ambientOcclusion = true;
            public boolean cullFace = true;
        }

        public int x1 = 0, y1 = 0, z1 = 0;
        public int x2 = 16, y2 = 16, z2 = 16;
        public final Face[] faces = new Face[6];

    }

    public Identifier modelName;
    public Map<String, Pixmap> textures = new HashMap<>();
    public List<Cuboid> cuboids = new ArrayList<>();

    public BlockModelGenerator(Identifier modelName) {
        this.modelName = modelName;
    }

    public String getModelTextureName(String textureName) {
        return modelName + "_" + textureName;
    }

    public void createTexture(String textureName, Pixmap texture) {
        textures.put(getModelTextureName(textureName), texture);
    }

    public Cuboid createCuboid(int x1, int y1, int z1, int x2, int y2, int z2) {
        Cuboid cuboid = new Cuboid();
        cuboid.x1 = x1;
        cuboid.y1 = y1;
        cuboid.z1 = z1;
        cuboid.x2 = x2;
        cuboid.y2 = y2;
        cuboid.z2 = z2;
        cuboid.faces[Cuboid.LOCAL_NEG_X] = new Cuboid.Face();
        cuboid.faces[Cuboid.LOCAL_NEG_X].id = "localNegX";
        cuboid.faces[Cuboid.LOCAL_POS_X] = new Cuboid.Face();
        cuboid.faces[Cuboid.LOCAL_POS_X].id = "localPosX";
        cuboid.faces[Cuboid.LOCAL_NEG_Y] = new Cuboid.Face();
        cuboid.faces[Cuboid.LOCAL_NEG_Y].id = "localNegY";
        cuboid.faces[Cuboid.LOCAL_POS_Y] = new Cuboid.Face();
        cuboid.faces[Cuboid.LOCAL_POS_Y].id = "localPosY";
        cuboid.faces[Cuboid.LOCAL_NEG_Z] = new Cuboid.Face();
        cuboid.faces[Cuboid.LOCAL_NEG_Z].id = "localNegZ";
        cuboid.faces[Cuboid.LOCAL_POS_Z] = new Cuboid.Face();
        cuboid.faces[Cuboid.LOCAL_POS_Z].id = "localPosZ";
        cuboids.add(cuboid);
        return cuboid;
    }

    @Override
    public void register(BlockLoader loader) {
        for(String textureName : textures.keySet()) {
            loader.registerTexture(textureName, textures.get(textureName));
        }
    }

    @Override
    public String generateJson() {
        BlockModelFlux model = new BlockModelFlux();

        model.textures = new OrderedMap<>();
        for(String textureName : textures.keySet()) {
            BlockModelJsonTexture texture = new BlockModelJsonTexture();
            texture.fileName = textureName;
            model.textures.put(textureName, texture);
        }

        model.cuboids = new BlockModelFluxCuboid[cuboids.size()];
        for(Cuboid cuboid : cuboids) {
            BlockModelFluxCuboid cuboid1 = new BlockModelFluxCuboid();
            cuboid1.localBounds = new float[] { cuboid.x1, cuboid.y1, cuboid.z2, cuboid.x2, cuboid.y2, cuboid.z2 };
            cuboid1.faces = new OrderedMap<>();
            for(Cuboid.Face face : cuboid.faces) {
                BlockModelFluxCuboid.Face face1 = new BlockModelFluxCuboid.Face();
                face1.texture = face.texture;
                face1.uv = new float[] { face.u1, face.v1, face.u2, face.v2 };
                face1.uvRotation = face.uvRotation;
                face1.cullFace = face.cullFace;
                face1.ambientocclusion = face.ambientOcclusion;
                cuboid1.faces.put(face.id, face1);
            }
        }

        Json json = new Json();
        return json.toJson(model);
    }
}
