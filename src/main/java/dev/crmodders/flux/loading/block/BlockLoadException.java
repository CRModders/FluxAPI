package dev.crmodders.flux.loading.block;

public class BlockLoadException extends RuntimeException {
    public final String blockName;

    public BlockLoadException(String blockName, Throwable cause) {
        super(cause);
        this.blockName = blockName;
    }

}
