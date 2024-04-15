// Decompiled with: Procyon 0.6.0
// Class Version: 17
package dev.crmodders.flux.loading.block;

import finalforeach.cosmicreach.constants.AdjacentBitmask;
import finalforeach.cosmicreach.rendering.blockmodels.BlockModelJsonTexture;
import finalforeach.cosmicreach.rendering.shaders.ChunkShader;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.OrderedMap;

import java.util.List;

public class BlockModelFluxCuboid
{

    public static class Face
    {
        public float[] uv;
        public boolean ambientocclusion;
        public boolean cullFace;
        public String texture;
        public  int uvRotation;
        public transient int cullingMask;
        public  transient float x1;
        public  transient float y1;
        public  transient float z1;
        public  transient float x2;
        public  transient float y2;
        public  transient float z2;
        public  transient float midX1;
        public  transient float midY1;
        public  transient float midZ1;
        public  transient float midX2;
        public  transient float midY2;
        public  transient float midZ2;
        public  transient float uA;
        public  transient float vA;
        public  transient float uB;
        public  transient float vB;
        public  transient float uC;
        public  transient float vC;
        public  transient float uD;
        public  transient float vD;
        public  transient int modelUvIdxA;
        public  transient int modelUvIdxB;
        public  transient int modelUvIdxC;
        public  transient int modelUvIdxD;
        public  transient int vertexIndexA;
        public  transient int vertexIndexB;
        public  transient int vertexIndexC;
        public  transient int vertexIndexD;
        public  transient int aoBitmaskA1;
        public  transient int aoBitmaskA2;
        public  transient int aoBitmaskA3;
        public  transient int aoBitmaskB1;
        public  transient int aoBitmaskB2;
        public  transient int aoBitmaskB3;
        public  transient int aoBitmaskC1;
        public  transient int aoBitmaskC2;
        public  transient int aoBitmaskC3;
        public  transient int aoBitmaskD1;
        public  transient int aoBitmaskD2;
        public  transient int aoBitmaskD3;
    }

    public transient boolean isPosXFaceOccluding;
    public transient boolean isNegXFaceOccluding;
    public transient boolean isPosYFaceOccluding;
    public transient boolean isNegYFaceOccluding;
    public transient boolean isPosZFaceOccluding;
    public transient boolean isNegZFaceOccluding;
    public transient boolean isPosXFacePartOccluding;
    public transient boolean isNegXFacePartOccluding;
    public transient boolean isPosYFacePartOccluding;
    public transient boolean isNegYFacePartOccluding;
    public transient boolean isPosZFacePartOccluding;
    public transient boolean isNegZFacePartOccluding;

    public float[] localBounds;
    public OrderedMap<String, Face> faces;

    // LEGACY
    public BoundingBox getBoundingBox() {
        BoundingBox bb = new BoundingBox();
        bb.min.set(this.localBounds[0] / 16.0f, this.localBounds[1] / 16.0f, this.localBounds[2] / 16.0f);
        bb.max.set(this.localBounds[3] / 16.0f, this.localBounds[4] / 16.0f, this.localBounds[5] / 16.0f);
        bb.update();
        return bb;
    }

    public void initialize(BlockModelFlux model, List<Face> allFaces) {
        for (final ObjectMap.Entry<String, Face> kf : this.faces) {

            boolean isValidFace = true;
            Face f = kf.value;

            // normalize bounds
            float x1 = this.localBounds[0] / 16.0f;
            float y1 = this.localBounds[1] / 16.0f;
            float z1 = this.localBounds[2] / 16.0f;
            float x2 = this.localBounds[3] / 16.0f;
            float y2 = this.localBounds[4] / 16.0f;
            float z2 = this.localBounds[5] / 16.0f;

            // find min,max of bounds
            float minX = Math.min(x1, x2);
            float minY = Math.min(y1, y2);
            float minZ = Math.min(z1, z2);
            float maxX = Math.max(x1, x2);
            float maxY = Math.max(y1, y2);
            float maxZ = Math.max(z1, z2);

            float uvScale = (float)(ChunkShader.allBlocksTexSize / 16);

            // determine culling flags
            if (f.cullFace) {
                f.cullingMask = switch (kf.key) {
                    case "localNegX" -> AdjacentBitmask.NEG_X;
                    case "localPosX" -> AdjacentBitmask.POS_X;
                    case "localNegY" -> AdjacentBitmask.NEG_Y;
                    case "localPosY" -> AdjacentBitmask.POS_Y;
                    case "localNegZ" -> AdjacentBitmask.NEG_Z;
                    case "localPosZ" -> AdjacentBitmask.POS_Z;
                    default -> throw new IllegalArgumentException("faceDirection is not valid: " + kf.key);
                };
            }

            // keep this
            BlockModelJsonTexture t = model.getTexture(f.texture);
            switch (kf.key) {
                case "localNegX":
                    this.isNegXFaceOccluding |= minX == 0.0F && minY <= 0.0F && maxY >= 1.0F && minZ <= 0.0F && maxZ >= 1.0F;
                    this.isNegXFacePartOccluding |= minX == 0.0F;
                    f.vertexIndexA = 0;
                    f.aoBitmaskA1 = 64;
                    f.aoBitmaskA2 = 128;
                    f.aoBitmaskA3 = 512;
                    f.vertexIndexB = 1;
                    f.aoBitmaskB1 = 256;
                    f.aoBitmaskB2 = 128;
                    f.aoBitmaskB3 = 1024;
                    f.vertexIndexC = 5;
                    f.aoBitmaskC1 = 8192;
                    f.aoBitmaskC2 = 4096;
                    f.aoBitmaskC3 = 1024;
                    f.vertexIndexD = 4;
                    f.aoBitmaskD1 = 2048;
                    f.aoBitmaskD2 = 4096;
                    f.aoBitmaskD3 = 512;
                    f.x1 = x1;
                    f.y1 = y1;
                    f.z1 = z1;
                    f.x2 = x1;
                    f.y2 = y2;
                    f.z2 = z2;
                    f.midX1 = f.x1;
                    f.midY1 = f.y1;
                    f.midZ1 = f.z2;
                    f.midX2 = f.x1;
                    f.midY2 = f.y2;
                    f.midZ2 = f.z1;
                    f.uA = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vA = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    f.uB = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vB = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    f.uC = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vC = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    f.uD = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vD = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    break;
                case "localPosX":
                    this.isPosXFaceOccluding |= maxX == 1.0F && minY <= 0.0F && maxY >= 1.0F && minZ <= 0.0F && maxZ >= 1.0F;
                    this.isPosXFacePartOccluding |= maxX == 1.0F;
                    f.vertexIndexA = 2;
                    f.aoBitmaskA1 = 262144;
                    f.aoBitmaskA2 = 524288;
                    f.aoBitmaskA3 = 2097152;
                    f.vertexIndexB = 6;
                    f.aoBitmaskB1 = 8388608;
                    f.aoBitmaskB2 = 16777216;
                    f.aoBitmaskB3 = 2097152;
                    f.vertexIndexC = 7;
                    f.aoBitmaskC1 = 33554432;
                    f.aoBitmaskC2 = 16777216;
                    f.aoBitmaskC3 = 4194304;
                    f.vertexIndexD = 3;
                    f.aoBitmaskD1 = 1048576;
                    f.aoBitmaskD2 = 524288;
                    f.aoBitmaskD3 = 4194304;
                    f.x1 = x2;
                    f.y1 = y1;
                    f.z1 = z1;
                    f.x2 = x2;
                    f.y2 = y2;
                    f.z2 = z2;
                    f.midX1 = f.x1;
                    f.midY1 = f.y2;
                    f.midZ1 = f.z1;
                    f.midX2 = f.x1;
                    f.midY2 = f.y1;
                    f.midZ2 = f.z2;
                    f.uA = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vA = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    f.uB = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vB = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    f.uC = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vC = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    f.uD = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vD = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    break;
                case "localNegY":
                    this.isNegYFaceOccluding |= minX <= 0.0F && maxX >= 1.0F && minY == 0.0F && minZ <= 0.0F && maxZ >= 1.0F;
                    this.isNegYFacePartOccluding |= minY == 0.0F;
                    f.vertexIndexA = 0;
                    f.aoBitmaskA1 = 64;
                    f.aoBitmaskA2 = 128;
                    f.aoBitmaskA3 = 16384;
                    f.vertexIndexB = 2;
                    f.aoBitmaskB1 = 262144;
                    f.aoBitmaskB2 = 524288;
                    f.aoBitmaskB3 = 16384;
                    f.vertexIndexC = 3;
                    f.aoBitmaskC1 = 1048576;
                    f.aoBitmaskC2 = 524288;
                    f.aoBitmaskC3 = 32768;
                    f.vertexIndexD = 1;
                    f.aoBitmaskD1 = 256;
                    f.aoBitmaskD2 = 128;
                    f.aoBitmaskD3 = 32768;
                    f.x1 = x1;
                    f.y1 = y1;
                    f.z1 = z1;
                    f.x2 = x2;
                    f.y2 = y1;
                    f.z2 = z2;
                    f.midX1 = f.x2;
                    f.midY1 = f.y1;
                    f.midZ1 = f.z1;
                    f.midX2 = f.x1;
                    f.midY2 = f.y1;
                    f.midZ2 = f.z2;
                    f.uA = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vA = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    f.uB = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vB = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    f.uC = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vC = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    f.uD = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vD = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    break;
                case "localPosY":
                    this.isPosYFaceOccluding |= minX <= 0.0F && maxX >= 1.0F && maxY == 1.0F && minZ <= 0.0F && maxZ >= 1.0F;
                    this.isPosYFacePartOccluding |= maxY == 1.0F;
                    f.vertexIndexA = 4;
                    f.aoBitmaskA1 = 2048;
                    f.aoBitmaskA2 = 4096;
                    f.aoBitmaskA3 = 65536;
                    f.vertexIndexB = 5;
                    f.aoBitmaskB1 = 8192;
                    f.aoBitmaskB2 = 4096;
                    f.aoBitmaskB3 = 131072;
                    f.vertexIndexC = 7;
                    f.aoBitmaskC1 = 33554432;
                    f.aoBitmaskC2 = 16777216;
                    f.aoBitmaskC3 = 131072;
                    f.vertexIndexD = 6;
                    f.aoBitmaskD1 = 8388608;
                    f.aoBitmaskD2 = 16777216;
                    f.aoBitmaskD3 = 65536;
                    f.x1 = x1;
                    f.y1 = y2;
                    f.z1 = z1;
                    f.x2 = x2;
                    f.y2 = y2;
                    f.z2 = z2;
                    f.midX1 = f.x1;
                    f.midY1 = f.y1;
                    f.midZ1 = f.z2;
                    f.midX2 = f.x2;
                    f.midY2 = f.y1;
                    f.midZ2 = f.z1;
                    f.uA = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vA = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    f.uB = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vB = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    f.uC = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vC = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    f.uD = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vD = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    break;
                case "localNegZ":
                    this.isNegZFaceOccluding |= minX <= 0.0F && maxX >= 1.0F && minY <= 0.0F && maxY >= 1.0F && minZ == 0.0F;
                    this.isNegZFacePartOccluding |= minZ == 0.0F;
                    f.vertexIndexA = 0;
                    f.aoBitmaskA1 = 64;
                    f.aoBitmaskA2 = 16384;
                    f.aoBitmaskA3 = 512;
                    f.vertexIndexB = 4;
                    f.aoBitmaskB1 = 2048;
                    f.aoBitmaskB2 = 65536;
                    f.aoBitmaskB3 = 512;
                    f.vertexIndexC = 6;
                    f.aoBitmaskC1 = 8388608;
                    f.aoBitmaskC2 = 65536;
                    f.aoBitmaskC3 = 2097152;
                    f.vertexIndexD = 2;
                    f.aoBitmaskD1 = 262144;
                    f.aoBitmaskD2 = 16384;
                    f.aoBitmaskD3 = 2097152;
                    f.x1 = x1;
                    f.y1 = y1;
                    f.z1 = z1;
                    f.x2 = x2;
                    f.y2 = y2;
                    f.z2 = z1;
                    f.midX1 = f.x1;
                    f.midY1 = f.y2;
                    f.midZ1 = f.z1;
                    f.midX2 = f.x2;
                    f.midY2 = f.y1;
                    f.midZ2 = f.z1;
                    f.uA = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vA = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    f.uB = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vB = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    f.uC = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vC = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    f.uD = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vD = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    break;
                case "localPosZ":
                    this.isPosZFaceOccluding |= minX <= 0.0F && maxX >= 1.0F && minY <= 0.0F && maxY >= 1.0F && maxZ == 1.0F;
                    this.isPosZFacePartOccluding |= maxZ == 1.0F;
                    f.vertexIndexA = 1;
                    f.aoBitmaskA1 = 256;
                    f.aoBitmaskA2 = 32768;
                    f.aoBitmaskA3 = 1024;
                    f.vertexIndexB = 3;
                    f.aoBitmaskB1 = 1048576;
                    f.aoBitmaskB2 = 32768;
                    f.aoBitmaskB3 = 4194304;
                    f.vertexIndexC = 7;
                    f.aoBitmaskC1 = 33554432;
                    f.aoBitmaskC2 = 131072;
                    f.aoBitmaskC3 = 4194304;
                    f.vertexIndexD = 5;
                    f.aoBitmaskD1 = 8192;
                    f.aoBitmaskD2 = 131072;
                    f.aoBitmaskD3 = 1024;
                    f.x1 = x1;
                    f.y1 = y1;
                    f.z1 = z2;
                    f.x2 = x2;
                    f.y2 = y2;
                    f.z2 = z2;
                    f.midX1 = f.x2;
                    f.midY1 = f.y1;
                    f.midZ1 = f.z1;
                    f.midX2 = f.x1;
                    f.midY2 = f.y2;
                    f.midZ2 = f.z1;
                    f.uA = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vA = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    f.uB = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vB = (t.uv[1] + f.uv[3] / 16.0F) / uvScale;
                    f.uC = (t.uv[0] + f.uv[2] / 16.0F) / uvScale;
                    f.vC = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    f.uD = (t.uv[0] + f.uv[0] / 16.0F) / uvScale;
                    f.vD = (t.uv[1] + f.uv[1] / 16.0F) / uvScale;
                    break;
                default:
                    isValidFace = false;
            }

            int tmpRotation = f.uvRotation / 90;
            while(tmpRotation > 0) {
                float tmpU = f.uA;
                float tmpV = f.vA;
                f.uA = f.uB;
                f.vA = f.vB;
                f.uB = f.uC;
                f.vB = f.vC;
                f.uC = f.uD;
                f.vC = f.vD;
                f.uD = tmpU;
                f.vD = tmpV;
                tmpRotation--;
            }

            f.modelUvIdxA = createFaceUBOFloatsIdx(f.uA, f.vA);
            f.modelUvIdxB = createFaceUBOFloatsIdx(f.uB, f.vB);
            f.modelUvIdxC = createFaceUBOFloatsIdx(f.uC, f.vC);
            f.modelUvIdxD = createFaceUBOFloatsIdx(f.uD, f.vD);

            if (isValidFace) allFaces.add(f);

        }
    }

    private static int createFaceUBOFloatsIdx(final float u, final float v) {
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
