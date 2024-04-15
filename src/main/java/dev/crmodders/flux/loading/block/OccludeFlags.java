package dev.crmodders.flux.loading.block;

import finalforeach.cosmicreach.rendering.blockmodels.BlockModel;

public class OccludeFlags {
    public boolean posX;
    public boolean negX;
    public boolean posY;
    public boolean negY;
    public boolean posZ;
    public boolean negZ;

    public OccludeFlags() {
        this(false);
    }

    public OccludeFlags(boolean value) {
        posX=value;negX=value;
        posY=value;negY=value;
        posZ=value;negZ=value;
    }

    public void orWith(OccludeFlags model) {
        model.posX |= posX;
        model.negX |= negX;
        model.posY |= posY;
        model.negY |= negY;
        model.posZ |= posZ;
        model.negZ |= negZ;
    }

    public void orWith(BlockModel model) {
        model.isPosXFaceOccluding |= posX;
        model.isNegXFaceOccluding |= negX;
        model.isPosYFaceOccluding |= posY;
        model.isNegYFaceOccluding |= negY;
        model.isPosZFaceOccluding |= posZ;
        model.isNegZFaceOccluding |= negZ;
    }

    public void orWithPart(BlockModel model) {
        model.isPosXFacePartOccluding |= posX;
        model.isNegXFacePartOccluding |= negX;
        model.isPosYFacePartOccluding |= posY;
        model.isNegYFacePartOccluding |= negY;
        model.isPosZFacePartOccluding |= posZ;
        model.isNegZFacePartOccluding |= negZ;
    }

    @Override
    public String toString() {
        return "OccludeFlags[" +
                "posX=" + posX +
                ", negX=" + negX +
                ", posY=" + posY +
                ", negY=" + negY +
                ", posZ=" + posZ +
                ", negZ=" + negZ +
                ']';
    }
}
