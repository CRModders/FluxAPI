package dev.crmodders.flux.api.v6.factories;

import dev.crmodders.flux.loading.block.BlockLoader;

public interface IGenerator {
    void register(BlockLoader loader);
    String generateJson();
}
