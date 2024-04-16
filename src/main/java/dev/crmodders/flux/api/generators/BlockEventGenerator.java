package dev.crmodders.flux.api.generators;

import dev.crmodders.flux.api.factories.IGenerator;
import dev.crmodders.flux.loading.block.BlockLoader;
import dev.crmodders.flux.tags.Identifier;

public class BlockEventGenerator implements IGenerator {

    public Identifier eventId;

    public BlockEventGenerator(Identifier blockId, String eventName) {
        this.eventId = new Identifier(blockId.namespace, blockId.name + "_" + eventName + "_events");
    }

    @Override
    public void register(BlockLoader loader) {}

    @Override
    public String generateJson() {
        throw new IllegalStateException("Not implemented");
    }
}
