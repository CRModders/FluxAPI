package dev.crmodders.flux.vectors;

import com.badlogic.gdx.math.Vector4;
import finalforeach.cosmicreach.io.CosmicReachBinaryDeserializer;
import finalforeach.cosmicreach.io.CosmicReachBinarySerializer;
import finalforeach.cosmicreach.io.ICosmicReachBinarySerializable;

public class Vec4 extends Vector4 implements ICosmicReachBinarySerializable {

    public Vec4(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public void read(CosmicReachBinaryDeserializer deserializer) {
        this.x = deserializer.readInt("x", (int) this.x);
        this.y = deserializer.readInt("y", (int) this.y);
        this.z = deserializer.readInt("z", (int) this.z);
        this.w = deserializer.readInt("w", (int) this.w);
    }

    @Override
    public void write(CosmicReachBinarySerializer serializer) {
        serializer.writeInt("x", (int) this.x);
        serializer.writeInt("y", (int) this.y);
        serializer.writeInt("z", (int) this.z);
        serializer.writeInt("w", (int) this.w);
    }
}
