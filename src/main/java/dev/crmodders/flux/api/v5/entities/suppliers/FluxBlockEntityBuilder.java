package dev.crmodders.flux.api.v5.entities.suppliers;

import com.badlogic.gdx.utils.OrderedMap;
import dev.crmodders.flux.api.v5.entities.FluxBlockEntityImpl;
import dev.crmodders.flux.api.v5.entities.ZoneBlockEntityPair;
import dev.crmodders.flux.api.v5.entities.api.IFluxBlockEntity;
import dev.crmodders.flux.loading.stages.RegisterBlockEntities;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.BlockPositionUtil;
import dev.crmodders.flux.vectors.Vec3;
import finalforeach.cosmicreach.blockentities.BlockEntity;
import finalforeach.cosmicreach.blockentities.BlockEntityCreator;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.Zone;

@FunctionalInterface
public interface FluxBlockEntityBuilder {

    FluxBlockEntityImpl get(Block block, Vec3 pos, OrderedMap<String, ?> params, OrderedMap<String, ?> blockParams);

    default BlockEntityCreator convertBuilder() {
        return (block, x, y, z) -> {
            IFluxBlockEntity entity = get(block, new Vec3(x, y, z), block.blockEntityParams, block.defaultParams);
            boolean isChild = entity.getClass().getSuperclass() == BlockEntity.class;

            if (!isChild) {
                throw new RuntimeException("BLOCK ENTITY IS NOT CHILD CLASS OF COSMIC REACH'S BLOCK ENTITY");
            }

            FluxRegistries.BLOCK_ENTITIES.register(Identifier.fromString(block.blockEntityId + "_" + RegisterBlockEntities.Count), new ZoneBlockEntityPair(
                    entity,
                    InGame.getLocalPlayer().getZone(InGame.world)
            ));

            return (BlockEntity) entity;
        };
    }

}
