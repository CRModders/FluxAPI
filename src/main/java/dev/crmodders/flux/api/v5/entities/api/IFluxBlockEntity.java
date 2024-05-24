package dev.crmodders.flux.api.v5.entities.api;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import dev.crmodders.flux.api.v5.entities.suppliers.FluxBlockEntityBuilder;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.vectors.Vec3;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.io.CosmicReachBinaryDeserializer;
import finalforeach.cosmicreach.io.CosmicReachBinarySerializer;
import finalforeach.cosmicreach.io.ICosmicReachBinarySerializable;
import finalforeach.cosmicreach.world.Zone;

public interface IFluxBlockEntity extends ICosmicReachBinarySerializable {

    // Triggers
    default void onInteract(Zone entityZone, BlockPosition pos, boolean justPressed, boolean held, double sinceLastInteration) {}
    default void onCreate() {}
    default void onRemove() {}
    default void onTick() {}

    // Render Method
    default void renderEntity(PerspectiveCamera camera) {}

    // De/Serialization methods
    default void read(CosmicReachBinaryDeserializer deserializer) {
        getId().read(deserializer);
        getPos().read(deserializer);
    }

    default void write(CosmicReachBinarySerializer serializer) {
        getId().write(serializer);
        getPos().write(serializer);
    }

    // Getters
    Vec3 getPos();
    Identifier getId();

    // Helper Methods
    static void registerBlockEntityBuilder(Identifier id, FluxBlockEntityBuilder builder) {
        FluxRegistries.BLOCK_ENTITY_BUILDERS.register(id, builder);
    }

}
