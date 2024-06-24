package dev.crmodders.flux.engine.blocks;

import dev.crmodders.flux.block.IModBlock;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.blocks.Block;

public class BlockLoadException extends RuntimeException {

    public final IModBlock iModBlock;
    public final String blockName;
    public final Identifier blockId;
    public final String json;
    public final Block block;

    public BlockLoadException(IModBlock iModBlock, String blockName, Identifier blockId, String json, Block block, Throwable cause) {
        super(cause);
        this.iModBlock = iModBlock;
        this.blockName = blockName;
        this.blockId = blockId;
        this.json = json;
        this.block = block;
    }
}
