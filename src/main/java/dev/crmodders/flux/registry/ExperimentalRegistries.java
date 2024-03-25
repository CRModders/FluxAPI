package dev.crmodders.flux.registry;

import dev.crmodders.flux.api.resource.ResourceObject;
import finalforeach.cosmicreach.gamestates.GameState;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.registry.registries.DynamicRegistry;

public class ExperimentalRegistries {

    public static DynamicRegistry<GameState> DynamicGameStateRegistry = DynamicRegistry.create();
    public static DynamicRegistry<BlockGenerator.FactoryFinalizer> FactoryFinalizers = DynamicRegistry.create();

    public static DynamicRegistry<ResourceObject> ResourceRegistry = DynamicRegistry.create();
}
