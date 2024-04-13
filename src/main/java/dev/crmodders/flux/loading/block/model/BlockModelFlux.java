// Decompiled with: Procyon 0.6.0
// Class Version: 17
package dev.crmodders.flux.loading.block.model;

import java.util.HashMap;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.Color;
import finalforeach.cosmicreach.RuntimeInfo;
import finalforeach.cosmicreach.rendering.IMeshData;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModel;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJsonCuboid;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJsonCuboidFace;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJsonTexture;
import finalforeach.cosmicreach.rendering.shaders.ChunkShader;
import finalforeach.cosmicreach.GameAssetLoader;

import java.util.Map;

public class BlockModelFlux extends BlockModel
{
    private static final Map<BlockModelJsonInstanceKey, BlockModelFlux> models;
    private String parent;
    public int rotXZ;
    public String name;
    private OrderedMap<String, BlockModelJsonTexture> textures;
    private BlockModelFluxCuboid[] cuboids;
    private transient BlockModelFluxCuboidFace[] allFaces;
    public static final boolean useIndices;
    Boolean canGreedyCombine;
    public int uvUBOIndex;
    
    public static BlockModelFlux getInstance(final String modelName, final int rotXZ) {
        final BlockModelJsonInstanceKey key = new BlockModelJsonInstanceKey(modelName, rotXZ);
        if (!BlockModelFlux.models.containsKey(key)) {
            final String jsonStr = GameAssetLoader.loadAsset("models/blocks/" + modelName + ".json").readString();
            final BlockModelFlux b = fromJson(jsonStr, rotXZ);
            b.name = modelName;
            BlockModelFlux.models.put(key, b);
        }
        return BlockModelFlux.models.get(key);
    }
    
    public static BlockModelFlux getInstanceFromJsonStr(final String modelName, final String modelJson, final int rotXZ) {
        final BlockModelJsonInstanceKey key = new BlockModelJsonInstanceKey(modelName, rotXZ);
        if (!BlockModelFlux.models.containsKey(key)) {
            final BlockModelFlux b = fromJson(modelJson, rotXZ);
            b.name = modelName;
            BlockModelFlux.models.put(key, b);
        }
        return BlockModelFlux.models.get(key);
    }
    
    public static BlockModelFlux fromJson(final String modelJson, final int rotXZ) {
        final Json json = new Json();
        final BlockModelFlux b = json.fromJson(BlockModelFlux.class, modelJson);
        if (b.parent != null && !b.parent.isEmpty()) {
            final BlockModelFlux parent = getInstance(b.parent, 0);
            if (b.cuboids == null && parent.cuboids != null) {
                b.cuboids = json.fromJson(parent.cuboids.getClass(), json.toJson(parent.cuboids));
            }
            if (b.textures == null && parent.textures != null) {
                b.textures = json.fromJson(parent.textures.getClass(), json.toJson(parent.textures));
            }
        }
        b.rotXZ = rotXZ;
        return b;
    }
    
    private BlockModelFlux() {
    }
    
    public OrderedMap<String, BlockModelJsonTexture> getTextures() {
        return this.textures;
    }
    
    public void initialize() {
        if (this.textures != null) {
            for (final BlockModelJsonTexture t : this.textures.values()) {
                if (t.fileName != null) {
                    // TODO, Problem Source
                    t.uv = ChunkShader.addToAllBlocksTexture(null, t);
                }
            }
        }
        if (this.cuboids != null && this.textures != null) {
            final Array<BlockModelFluxCuboidFace> faces = new Array<>(BlockModelFluxCuboidFace.class);
            for (final BlockModelFluxCuboid c : this.cuboids) {
                final float boundsX1 = c.localBounds[0];
                final float boundsZ1 = c.localBounds[2];
                final float boundsX2 = c.localBounds[3];
                final float boundsZ2 = c.localBounds[5];
                final BlockModelFluxCuboidFace tmpNegX = c.faces.get("localNegX");
                final BlockModelFluxCuboidFace tmpPosX = c.faces.get("localPosX");
                final BlockModelFluxCuboidFace tmpNegY = c.faces.get("localNegY");
                final BlockModelFluxCuboidFace tmpPosY = c.faces.get("localPosY");
                final BlockModelFluxCuboidFace tmpNegZ = c.faces.get("localNegZ");
                final BlockModelFluxCuboidFace tmpPosZ = c.faces.get("localPosZ");
                switch (rotXZ) {
                    case 90: {
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
                c.initialize(this.textures, faces);
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
            for (final BlockModelFluxCuboid c : this.cuboids) {
                if (this.boundingBox.max.epsilonEquals(this.boundingBox.min)) {
                    this.boundingBox = c.getBoundingBox();
                }
                else {
                    this.boundingBox.ext(c.getBoundingBox());
                }
            }
            this.allFaces = faces.toArray();
        }
        else {
            this.allFaces = new BlockModelFluxCuboidFace[0];
        }
        if (this.cuboids == null || this.cuboids.length == 0 || this.boundingBox.max.epsilonEquals(this.boundingBox.min)) {
            this.boundingBox.min.set(0.0f, 0.0f, 0.0f);
            this.boundingBox.max.set(1.0f, 1.0f, 1.0f);
        }
        this.boundingBox.update();
    }

    @Override
    public void addVertices(final IMeshData meshData, final int bx, final int by, final int bz, final int opaqueBitmask, final short[] blockLightLevels, final int[] skyLightLevels) {
        final IntArray indices = meshData.getIndices();
        meshData.ensureVerticesCapacity(6 * this.allFaces.length * 7);
        for (int fi = 0; fi < this.allFaces.length; ++fi) {
            final BlockModelFluxCuboidFace f = this.allFaces[fi];
            if ((opaqueBitmask & f.cullingMask) == 0x0) {
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
                    aoIdA = ((opaqueBitmask & f.aoBitmaskA1) == 0x0 || (opaqueBitmask & f.aoBitmaskA2) == 0x0 || (opaqueBitmask & f.aoBitmaskA3) == 0x0) ? 1 : 0;
                    aoIdB = ((opaqueBitmask & f.aoBitmaskB1) == 0x0 || (opaqueBitmask & f.aoBitmaskB2) == 0x0 || (opaqueBitmask & f.aoBitmaskB3) == 0x0) ? 1 : 0;
                    aoIdC = ((opaqueBitmask & f.aoBitmaskC1) == 0x0 || (opaqueBitmask & f.aoBitmaskC2) == 0x0 || (opaqueBitmask & f.aoBitmaskC3) == 0x0) ? 1 : 0;
                    aoIdD = ((opaqueBitmask & f.aoBitmaskD1) == 0x0 || (opaqueBitmask & f.aoBitmaskD2) == 0x0 || (opaqueBitmask & f.aoBitmaskD3) == 0x0) ? 1 : 0;
                }
                else {
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
        if (this.cuboids.length == 1) {
            final BlockModelFluxCuboid c = this.cuboids[0];
            if (c.faces.size == 6 && c.localBounds[0] == 0.0f && c.localBounds[1] == 0.0f && c.localBounds[2] == 0.0f && c.localBounds[3] == 16.0f && c.localBounds[4] == 16.0f && c.localBounds[5] == 16.0f) {
                for (final BlockModelFluxCuboidFace f : c.faces.values()) {
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
    
    static {
        models = new HashMap<BlockModelJsonInstanceKey, BlockModelFlux>();
        useIndices = !RuntimeInfo.useSharedIndices;
    }
    
    public record BlockModelJsonInstanceKey(String modelName, int rotXZ) {}
}
