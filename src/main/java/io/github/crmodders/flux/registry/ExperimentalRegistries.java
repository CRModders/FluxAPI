package io.github.crmodders.flux.registry;

import finalforeach.cosmicreach.gamestates.GameState;
import io.github.crmodders.flux.api.generators.BlockGenerator;
import io.github.crmodders.flux.registry.registries.DynamicRegistry;

public class ExperimentalRegistries {

    public static DynamicRegistry<GameState> DynamicGameStateRegistry = DynamicRegistry.create();
    public static DynamicRegistry<BlockGenerator.FactoryFinalizer> FactoryFinalizers = DynamicRegistry.create();

}
