package dev.crmodders.flux.vectors;

import com.badlogic.gdx.math.Vector2;
import finalforeach.cosmicreach.io.CosmicReachBinaryDeserializer;
import finalforeach.cosmicreach.io.CosmicReachBinarySerializer;
import finalforeach.cosmicreach.io.ICosmicReachBinarySerializable;

public class Vec2 extends Vector2 implements ICosmicReachBinarySerializable {

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void read(CosmicReachBinaryDeserializer deserializer) {
        this.x = deserializer.readInt("x", (int) this.x);
        this.y = deserializer.readInt("y", (int) this.y);
    }

    @Override
    public void write(CosmicReachBinarySerializer serializer) {
        serializer.writeInt("x", (int) this.x);
        serializer.writeInt("y", (int) this.y);
    }

}
