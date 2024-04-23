package dev.crmodders.flux.api.factories;

import dev.crmodders.flux.engine.blocks.BlockLoader;

public interface IGenerator {
    void register(BlockLoader loader);
    String generateJson();
}
