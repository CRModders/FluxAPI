// Decompiled with: Procyon 0.6.0
// Class Version: 17
package dev.crmodders.flux.loading.block;

import java.lang.StringBuilder;
import java.util.*;

import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.Color;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.RuntimeInfo;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.rendering.IMeshData;
import finalforeach.cosmicreach.rendering.blockmodels.*;
import finalforeach.cosmicreach.rendering.shaders.ChunkShader;

public class BlockModelFlux extends BlockModel {

    public static class Instantiator implements IBlockModelInstantiator {

        public final Map<InstanceKey, BlockModelFlux> models = new LinkedHashMap<>();

        @Override
        public BlockModel getInstance(String modelName, int rotXZ) {
            final InstanceKey key = new InstanceKey(modelName, rotXZ);
            if(models.containsKey(key)) {
                return models.get(key);
            }

            String modelJson = GameAssetLoader.loadAsset("models/blocks/" + modelName + ".json").readString();
            BlockModelFlux model = fromJson(modelJson, modelName, rotXZ);

            if(model.parent != null) {
                getInstance(model.parent, rotXZ);
            }

            models.put(key, model);
            return model;
        }

        public BlockModel createFromJson(String modelName, int rotXZ, String modelJson) {
            final InstanceKey key = new InstanceKey(modelName, rotXZ);
            if(models.containsKey(key)) {
                return models.get(key);
            }

            BlockModelFlux model = fromJson(modelJson, modelName, rotXZ);

            if(model.parent != null) {
                getInstance(model.parent, rotXZ);
            }

            models.put(key, model);
            return model;
        }

        @Override
        public void createSlabInstance(String modelName, BlockState blockState, String modelSlabType, int rotXZ) {
            final InstanceKey key = new InstanceKey(modelName, rotXZ);
            if(models.containsKey(key)) {
                return;
            }
            Json json = new Json();
            BlockModelFlux oldModel = (BlockModelFlux) blockState.getModel();

            StringBuilder slabModelJson = new StringBuilder();
            slabModelJson.append("{\"parent\":\"");
            slabModelJson.append(modelSlabType);
            slabModelJson.append("\", textures:");
            slabModelJson.append(json.toJson(oldModel.getTextures()));
            slabModelJson.append("}");

            String modelJson = slabModelJson.toString();
            BlockModelFlux model = fromJson(modelJson, modelName, rotXZ);

            if(model.parent != null) {
                getInstance(model.parent, rotXZ);
            }

            models.put(key, model);
        }

        private int getNumberOfParents(BlockModelFlux model) {
            int n = 0;
            String parent = model.parent;
            while(parent != null) {
                BlockModelFlux parentModel = null;

                InstanceKey parentKey;
                if(models.containsKey(parentKey = new InstanceKey(parent, 0))) parentModel = models.get(parentKey);
                else if(models.containsKey(parentKey = new InstanceKey(parent, 90))) parentModel = models.get(parentKey);
                else if(models.containsKey(parentKey = new InstanceKey(parent, 180))) parentModel = models.get(parentKey);
                else if(models.containsKey(parentKey = new InstanceKey(parent, 270))) parentModel = models.get(parentKey);

                parent = parentModel == null ? null : parentModel.parent;
                n++;
            }
            return n;
        }

        public int compare(BlockModelFlux o1, BlockModelFlux o2) {
            return Integer.compare(getNumberOfParents(o1), getNumberOfParents(o2));
        }

        public List<BlockModelFlux> sort() {
            List<BlockModelFlux> models = new ArrayList<>(this.models.values());
            models.sort(this::compare);
            return models;
        }

    }

    public static BlockModelFlux fromJson(String modelJson, String modelName, int rotXZ) {
        Json json = new Json();
        BlockModelFlux model = json.fromJson(BlockModelFlux.class, modelJson);
        model.modelJson = modelJson; // TODO maybe remove this (high memory usage)
        model.modelName = modelName;
        model.rotXZ = rotXZ;
        return model;
    }

    public record InstanceKey(String modelName, int rotXZ) {}

    public static final boolean useIndices = !RuntimeInfo.useSharedIndices;
    public transient String modelJson;
    public transient String modelName;
    public transient int rotXZ;
    public transient BlockModelFluxCuboid.Face[] allFaces;
    public transient Boolean canGreedyCombine;
    public transient boolean initialized = false;

    public String parent;
    public OrderedMap<String, BlockModelJsonTexture> textures;
    public BlockModelFluxCuboid[] cuboids;

    private BlockModelFlux() {
    }

    private static boolean endsWithOnce(String string, String endsWith) {
        return string.indexOf(endsWith) == string.length() - endsWith.length();
    }

    public String getModelSlabType() {
        String modelSlabType = "";
        if(endsWithOnce(modelName, "_model_slab_bottom")) modelSlabType = "slab_bottom";
        if(endsWithOnce(modelName, "_model_slab_top")) modelSlabType = "slab_top";
        if(endsWithOnce(modelName, "_model_slab_vertical")) modelSlabType = "slab_vertical";
        return modelSlabType;
    }

    public OrderedMap<String, BlockModelJsonTexture> getTextures() {
        return this.textures;
    }

    public BlockModelJsonTexture getTexture(String texName) {
        BlockModelJsonTexture t = textures.get(texName);
        if (t != null) {
            return t;
        }
        if (texName.equals("slab_top")) {
            return getTexture("top");
        }
        if (texName.equals("slab_bottom")) {
            return this.getTexture("bottom");
        }
        if (texName.equals("slab_side")) {
            return getTexture("side");
        }
        return textures.get("all");
    }
    
    public void initialize(BlockModelFlux parent) {

        if (parent != null) {

            if(!parent.initialized) {
                throw new RuntimeException("Parent not Initialized");
            }

            Json json = new Json();

            if (this.cuboids == null && parent.cuboids != null) {
                this.cuboids = json.fromJson(parent.cuboids.getClass(), json.toJson(parent.cuboids));
            }

            if (this.textures == null && parent.textures != null) {
                this.textures = json.fromJson(parent.textures.getClass(), json.toJson(parent.textures));
            }

        }

        if (this.textures != null) {
            for (final BlockModelJsonTexture t : this.textures.values()) {
                if (t.fileName != null) {
                    // TODO, Problem Source (if textures break)
                    t.uv = ChunkShader.addToAllBlocksTexture(null, t);
                }
            }
        }

        if(cuboids != null && textures != null) {
            List<BlockModelFluxCuboid.Face> faces = new ArrayList<>();
            for (BlockModelFluxCuboid c : this.cuboids) {

                final float boundsX1 = c.localBounds[0];
                final float boundsZ1 = c.localBounds[2];
                final float boundsX2 = c.localBounds[3];
                final float boundsZ2 = c.localBounds[5];

                BlockModelFluxCuboid.Face tmpNegX = c.faces.get("localNegX");
                BlockModelFluxCuboid.Face tmpPosX = c.faces.get("localPosX");
                BlockModelFluxCuboid.Face tmpNegY = c.faces.get("localNegY");
                BlockModelFluxCuboid.Face tmpPosY = c.faces.get("localPosY");
                BlockModelFluxCuboid.Face tmpNegZ = c.faces.get("localNegZ");
                BlockModelFluxCuboid.Face tmpPosZ = c.faces.get("localPosZ");

                // rotate model
                switch (rotXZ) {
                    case 90:{
                        c.localBounds[0] = boundsZ1;
                        c.localBounds[2] = boundsX1;
                        c.localBounds[3] = boundsZ2;
                        c.localBounds[5] = boundsX2;
                        c.faces.clear();
                        if (tmpPosX != null) {
                            c.faces.put("localPosZ", tmpPosX);
                        }
                        if (tmpNegX != null) {
                            c.faces.put("localNegZ", tmpNegX);
                        }
                        if (tmpNegY != null) {
                            tmpNegY.uvRotation = (tmpNegY.uvRotation - 90 + 360) % 360;
                            c.faces.put("localNegY", tmpNegY);
                        }
                        if (tmpPosY != null) {
                            tmpPosY.uvRotation = (tmpPosY.uvRotation + 90 + 360) % 360;
                            c.faces.put("localPosY", tmpPosY);
                        }
                        if (tmpNegZ != null) {
                            final float tmpU = tmpPosZ.uv[0];
                            tmpNegZ.uv[0] = tmpNegZ.uv[2];
                            tmpNegZ.uv[2] = tmpU;
                            c.faces.put("localPosX", tmpNegZ);
                        }
                        if (tmpPosZ != null) {
                            c.faces.put("localNegX", tmpPosZ);
                            break;
                        }
                        break;
                }
                    case 180: {
                        final float fxa = 16.0f - boundsX1;
                        final float fxb = 16.0f - boundsX2;
                        final float fza = 16.0f - boundsZ1;
                        final float fzb = 16.0f - boundsZ2;
                        c.localBounds[0] = Math.min(fxa, fxb);
                        c.localBounds[2] = Math.min(fza, fzb);
                        c.localBounds[3] = Math.max(fxa, fxb);
                        c.localBounds[5] = Math.max(fza, fzb);
                        c.faces.clear();
                        if (tmpPosX != null) {
                            c.faces.put("localPosX", tmpNegX);
                        }
                        if (tmpNegX != null) {
                            c.faces.put("localNegX", tmpPosX);
                        }
                        if (tmpNegY != null) {
                            c.faces.put("localNegY", tmpNegY);
                        }
                        if (tmpPosY != null) {
                            c.faces.put("localPosY", tmpPosY);
                        }
                        if (tmpNegZ != null) {
                            c.faces.put("localNegZ", tmpPosZ);
                        }
                        if (tmpPosZ != null) {
                            c.faces.put("localPosZ", tmpNegZ);
                            break;
                        }
                        break;
                    }
                    case 270: {
                        final float fxa = 16.0f - boundsX1;
                        final float fxb = 16.0f - boundsX2;
                        final float fza = 16.0f - boundsZ1;
                        final float fzb = 16.0f - boundsZ2;
                        c.localBounds[0] = Math.min(fza, fzb);
                        c.localBounds[2] = Math.min(fxa, fxb);
                        c.localBounds[3] = Math.max(fza, fzb);
                        c.localBounds[5] = Math.max(fxa, fxb);
                        c.faces.clear();
                        if (tmpNegX != null) {
                            c.faces.put("localPosZ", tmpNegX);
                        }
                        if (tmpPosX != null) {
                            c.faces.put("localNegZ", tmpPosX);
                        }
                        if (tmpNegY != null) {
                            tmpNegY.uvRotation = (tmpNegY.uvRotation - 90 + 360) % 360;
                            c.faces.put("localNegY", tmpNegY);
                        }
                        if (tmpPosY != null) {
                            tmpPosY.uvRotation = (tmpPosY.uvRotation + 90 + 360) % 360;
                            c.faces.put("localPosY", tmpPosY);
                        }
                        if (tmpPosZ != null) {
                            c.faces.put("localPosX", tmpPosZ);
                        }
                        if (tmpNegZ != null) {
                            final float tmpU2 = tmpPosZ.uv[0];
                            tmpNegZ.uv[0] = tmpNegZ.uv[2];
                            tmpNegZ.uv[2] = tmpU2;
                            c.faces.put("localNegX", tmpNegZ);
                            break;
                        }
                        break;
                    }
                }

                // init model
                c.initialize(this, faces);
                this.isNegXFaceOccluding |= c.isNegXFaceOccluding;
                this.isPosXFaceOccluding |= c.isPosXFaceOccluding;
                this.isNegYFaceOccluding |= c.isNegYFaceOccluding;
                this.isPosYFaceOccluding |= c.isPosYFaceOccluding;
                this.isNegZFaceOccluding |= c.isNegZFaceOccluding;
                this.isPosZFaceOccluding |= c.isPosZFaceOccluding;
                this.isNegXFacePartOccluding |= c.isNegXFacePartOccluding;
                this.isPosXFacePartOccluding |= c.isPosXFacePartOccluding;
                this.isNegYFacePartOccluding |= c.isNegYFacePartOccluding;
                this.isPosYFacePartOccluding |= c.isPosYFacePartOccluding;
                this.isNegZFacePartOccluding |= c.isNegZFacePartOccluding;
                this.isPosZFacePartOccluding |= c.isPosZFacePartOccluding;
            }

            // cache bounding boxes
            for (final BlockModelFluxCuboid c : this.cuboids) {
                if (this.boundingBox.max.epsilonEquals(this.boundingBox.min)) {
                    this.boundingBox = c.getBoundingBox();
                }
                else {
                    this.boundingBox.ext(c.getBoundingBox());
                }
            }

            allFaces = faces.toArray(BlockModelFluxCuboid.Face[]::new);
        } else {
            allFaces = new BlockModelFluxCuboid.Face[0];
        }

        if (cuboids == null || cuboids.length == 0 || boundingBox.max.epsilonEquals(boundingBox.min)) {
            boundingBox.min.set(0.0F, 0.0F, 0.0F);
            boundingBox.max.set(1.0F, 1.0F, 1.0F);
        }

        boundingBox.update();
        initialized = true;
    }

    @Override
    public void addVertices(final IMeshData meshData, final int bx, final int by, final int bz,  int opaqueBitmask, final short[] blockLightLevels, final int[] skyLightLevels) {
        final IntArray indices = meshData.getIndices();
        meshData.ensureVerticesCapacity(6 * this.allFaces.length * 7);
        for (final BlockModelFluxCuboid.Face f : this.allFaces) {
            if ((opaqueBitmask & f.cullingMask) == 0) {
                final float x1 = bx + f.x1;
                final float y1 = by + f.y1;
                final float z1 = bz + f.z1;
                final float x2 = bx + f.x2;
                final float y2 = by + f.y2;
                final float z2 = bz + f.z2;
                final float midX1 = bx + f.midX1;
                final float midY1 = by + f.midY1;
                final float midZ1 = bz + f.midZ1;
                final float midX2 = bx + f.midX2;
                final float midY2 = by + f.midY2;
                final float midZ2 = bz + f.midZ2;
                int aoIdA;
                int aoIdB;
                int aoIdC;
                int aoIdD;
                if (f.ambientocclusion) {
                    aoIdA = ((opaqueBitmask & f.aoBitmaskA1) == 0 ? 1 : 0) + ((opaqueBitmask & f.aoBitmaskA2) == 0 ? 1 : 0) + ((opaqueBitmask & f.aoBitmaskA3) == 0 ? 1 : 0);
                    aoIdB = ((opaqueBitmask & f.aoBitmaskB1) == 0 ? 1 : 0) + ((opaqueBitmask & f.aoBitmaskB2) == 0 ? 1 : 0) + ((opaqueBitmask & f.aoBitmaskB3) == 0 ? 1 : 0);
                    aoIdC = ((opaqueBitmask & f.aoBitmaskC1) == 0 ? 1 : 0) + ((opaqueBitmask & f.aoBitmaskC2) == 0 ? 1 : 0) + ((opaqueBitmask & f.aoBitmaskC3) == 0 ? 1 : 0);
                    aoIdD = ((opaqueBitmask & f.aoBitmaskD1) == 0 ? 1 : 0) + ((opaqueBitmask & f.aoBitmaskD2) == 0 ? 1 : 0) + ((opaqueBitmask & f.aoBitmaskD3) == 0 ? 1 : 0);
                } else {
                    aoIdA = 3;
                    aoIdB = 3;
                    aoIdC = 3;
                    aoIdD = 3;
                }
                final int viA = f.vertexIndexA;
                final int viB = f.vertexIndexB;
                final int viC = f.vertexIndexC;
                final int viD = f.vertexIndexD;
                final int i1 = this.addVert(meshData, x1, y1, z1, f.uA, f.vA, aoIdA, blockLightLevels[viA], skyLightLevels[viA], f.modelUvIdxA);
                final int i2 = this.addVert(meshData, midX1, midY1, midZ1, f.uB, f.vB, aoIdB, blockLightLevels[viB], skyLightLevels[viB], f.modelUvIdxB);
                final int i3 = this.addVert(meshData, x2, y2, z2, f.uC, f.vC, aoIdC, blockLightLevels[viC], skyLightLevels[viC], f.modelUvIdxC);
                final int i4 = this.addVert(meshData, midX2, midY2, midZ2, f.uD, f.vD, aoIdD, blockLightLevels[viD], skyLightLevels[viD], f.modelUvIdxD);
                if (BlockModelFlux.useIndices) {
                    indices.add(i1);
                    indices.add(i2);
                    indices.add(i3);
                    indices.add(i3);
                    indices.add(i4);
                    indices.add(i1);
                }
            }
        }
    }
    
    public int addVert(final IMeshData meshData, final float x, final float y, final float z, final float u, final float v, final int aoId, final short blockLight, int skyLight, final int uvIdx) {
        final FloatArray verts = meshData.getVertices();
        final float[] items = verts.items;
        final int size = verts.size;
        int numComponents = 5;
        if (RuntimeInfo.isMac) {
            ++numComponents;
        }
        final int indexOfCurVertex = size / numComponents;
        int r = 17 * ((blockLight & 0xF00) >> 8);
        int g = 17 * ((blockLight & 0xF0) >> 4);
        int b = 17 * (blockLight & 0xF);
        final float subAO = aoId / 4.0f + 0.25f;
        r *= (int)subAO;
        g *= (int)subAO;
        b *= (int)subAO;
        skyLight *= (int)(subAO * 17.0f);
        items[size] = x;
        items[size + 1] = y;
        items[size + 2] = z;
        items[size + 3] = Color.toFloatBits(r, g, b, skyLight);
        if (RuntimeInfo.isMac) {
            items[size + 4] = u;
            items[size + 5] = v;
        }
        else {
            items[size + 4] = Float.intBitsToFloat(uvIdx);
        }
        final FloatArray floatArray = verts;
        floatArray.size += numComponents;
        return indexOfCurVertex;
    }
    
    @Override
    public boolean isGreedyCube() {
        if (!isEmpty() && this.cuboids.length == 1) {
            final BlockModelFluxCuboid c = this.cuboids[0];
            if (c.faces.size == 6 && c.localBounds[0] == 0.0f && c.localBounds[1] == 0.0f && c.localBounds[2] == 0.0f && c.localBounds[3] == 16.0f && c.localBounds[4] == 16.0f && c.localBounds[5] == 16.0f) {
                for (final BlockModelFluxCuboid.Face f : c.faces.values()) {
                    boolean expectedUVs = true;
                    expectedUVs &= (f.uv[0] == 0.0f);
                    expectedUVs &= (f.uv[1] == 0.0f);
                    expectedUVs &= (f.uv[2] == 16.0f);
                    expectedUVs &= (f.uv[3] == 16.0f);
                    if (!expectedUVs) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean canGreedyCombine() {
        if (this.canGreedyCombine == null) {
            return this.isGreedyCube();
        }
        return this.canGreedyCombine;
    }
    
    @Override
    public boolean isEmpty() {
        return this.cuboids == null || this.cuboids.length == 0;
    }
    
    @Override
    public void getAllBoundingBoxes(final Array<BoundingBox> boundingBoxes, final int bx, final int by, final int bz) {
        boundingBoxes.clear();
        if (this.cuboids == null) {
            return;
        }
        for (final BlockModelFluxCuboid c : this.cuboids) {
            final BoundingBox bb = new BoundingBox();
            bb.min.set(c.localBounds[0] / 16.0f, c.localBounds[1] / 16.0f, c.localBounds[2] / 16.0f);
            bb.max.set(c.localBounds[3] / 16.0f, c.localBounds[4] / 16.0f, c.localBounds[5] / 16.0f);
            bb.min.add((float)bx, (float)by, (float)bz);
            bb.max.add((float)bx, (float)by, (float)bz);
            bb.update();
            boundingBoxes.add(bb);
        }
    }
}
