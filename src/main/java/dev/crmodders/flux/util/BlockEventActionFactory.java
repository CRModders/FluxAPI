package dev.crmodders.flux.util;

import dev.crmodders.flux.logging.LoggingAgent;
import dev.crmodders.flux.logging.api.MicroLogger;
import finalforeach.cosmicreach.blockevents.actions.BlockActionPlaySound2D;
import finalforeach.cosmicreach.blockevents.actions.BlockActionReplaceBlockState;

public class BlockEventActionFactory {

    private static final MicroLogger LOGGER = LoggingAgent.getLogger("Block Action Factory");

    public static BlockActionReplaceBlockState createReplaceBlockEvent(String blockStateId, int xOff, int yOff, int zOff) {
        BlockActionReplaceBlockState replace = new BlockActionReplaceBlockState();
        try {
            PrivUtils.setPrivField(replace, "blockStateId", blockStateId);
            PrivUtils.setPrivField(replace, "xOff", xOff);
            PrivUtils.setPrivField(replace, "yOff", yOff);
            PrivUtils.setPrivField(replace, "zOff", zOff);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error("createReplaceBlockEvent failed", e);
        }
        return replace;
    }

    public static BlockActionPlaySound2D createPlaySound2D(String sound, float volume, float pitch, float pan) {
        BlockActionPlaySound2D sound2D = new BlockActionPlaySound2D();
        try {
            PrivUtils.setPrivField(sound2D, "sound", sound);
            PrivUtils.setPrivField(sound2D, "volume", volume);
            PrivUtils.setPrivField(sound2D, "pitch", pitch);
            PrivUtils.setPrivField(sound2D, "pan", pan);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error("createPlaySound2D failed", e);
        }
        return sound2D;
    }

}
