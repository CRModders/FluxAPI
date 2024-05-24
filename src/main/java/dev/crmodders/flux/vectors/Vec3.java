package dev.crmodders.flux.vectors;

import com.badlogic.gdx.math.Vector3;
import finalforeach.cosmicreach.io.CosmicReachBinaryDeserializer;
import finalforeach.cosmicreach.io.CosmicReachBinarySerializer;
import finalforeach.cosmicreach.io.ICosmicReachBinarySerializable;

public class Vec3 extends Vector3 implements ICosmicReachBinarySerializable {

    public Vec3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void read(CosmicReachBinaryDeserializer deserializer) {
        this.x = deserializer.readInt("x", (int) this.x);
        this.y = deserializer.readInt("y", (int) this.y);
        this.z = deserializer.readInt("z", (int) this.z);
    }

    @Override
    public void write(CosmicReachBinarySerializer serializer) {
        serializer.writeInt("x", (int) this.x);
        serializer.writeInt("y", (int) this.y);
        serializer.writeInt("z", (int) this.z);
    }

}
