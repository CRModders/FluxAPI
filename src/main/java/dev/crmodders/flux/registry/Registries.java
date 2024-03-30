package dev.crmodders.flux.registry;

import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.generators.BlockGenerator;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.resource.ResourceObject;
import dev.crmodders.flux.localization.Language;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.registry.registries.DynamicRegistry;
import dev.crmodders.flux.registry.registries.FreezingRegistry;
import finalforeach.cosmicreach.world.blockevents.IBlockEventAction;

public class Registries {
    public static FreezingRegistry<IModBlock> BLOCKS = FreezingRegistry.create();
    public static FreezingRegistry<BlockEventDataExt> BLOCK_EVENTS = FreezingRegistry.create();
    public static FreezingRegistry<IBlockEventAction> BLOCK_EVENT_ACTIONS = FreezingRegistry.create();
    public static DynamicRegistry<BlockGenerator.FactoryFinalizer> BLOCK_FACTORY_FINALIZERS = DynamicRegistry.create();
    public static DynamicRegistry<ResourceObject> GAME_RESOURCES = DynamicRegistry.create();
    public static DynamicRegistry<LanguageFile> LANGUAGE_FILES = DynamicRegistry.create();
    public static DynamicRegistry<Language> LANGUAGES = DynamicRegistry.create();
}
