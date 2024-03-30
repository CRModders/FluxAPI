package dev.crmodders.flux.registry;

import dev.crmodders.flux.api.resource.ResourceObject;
import dev.crmodders.flux.localization.Language;
import dev.crmodders.flux.localization.LanguageFile;
import finalforeach.cosmicreach.gamestates.GameState;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.registry.registries.DynamicRegistry;

public class ExperimentalRegistries {

    public static DynamicRegistry<GameState> DynamicGameStateRegistry = DynamicRegistry.create();
    public static DynamicRegistry<BlockGenerator.FactoryFinalizer> FactoryFinalizers = DynamicRegistry.create();
    public static DynamicRegistry<ResourceObject> ResourceRegistry = DynamicRegistry.create();
    public static DynamicRegistry<LanguageFile> LanguageFiles = DynamicRegistry.create();
    public static DynamicRegistry<Language> Languages = DynamicRegistry.create();
}
