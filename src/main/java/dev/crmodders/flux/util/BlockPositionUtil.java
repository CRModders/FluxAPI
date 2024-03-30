package dev.crmodders.flux.util;

import com.badlogic.gdx.math.Vector3;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.world.Chunk;

public class BlockPositionUtil {

    public static BlockPosition getBlockPositionAtGlobalPos(int x, int y, int z) {
        Chunk c = InGame.getLocalPlayer().getZone(InGame.world).getChunkAtBlock(x, y, z);
        return new BlockPosition(c, x - c.blockX, y - c.blockY, z - c.blockZ);
    }


    public static BlockPosition getBlockPositionAtGlobalPos(Vector3 vec3) {
        Chunk c = InGame.getLocalPlayer().getZone(InGame.world).getChunkAtBlock((int) vec3.x, (int) vec3.y, (int) vec3.z);
        return new BlockPosition(c, ((int) vec3.x) - c.blockX, ((int) vec3.y) - c.blockY, ((int) vec3.z) - c.blockZ);
    }

}
