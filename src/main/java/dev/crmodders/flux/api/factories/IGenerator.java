package dev.crmodders.flux.api.factories;

import dev.crmodders.flux.loading.block.BlockLoader;

public interface IGenerator {
    void register(BlockLoader loader);
    String generateJson();
}
