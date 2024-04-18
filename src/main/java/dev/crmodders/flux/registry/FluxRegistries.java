package dev.crmodders.flux.registry;

import dev.crmodders.flux.annotations.FluxInternal;
import dev.crmodders.flux.annotations.Legacy;
import dev.crmodders.flux.annotations.Stable;
import dev.crmodders.flux.api.v5.generators.FactoryFinalizer;
import dev.crmodders.flux.api.v5.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.v6.block.FluxBlockAction;
import dev.crmodders.flux.api.v6.block.IModBlock;
import dev.crmodders.flux.api.v6.config.BasicConfig;
import dev.crmodders.flux.api.v6.factories.IFactory;
import dev.crmodders.flux.api.v6.resource.ResourceObject;
import dev.crmodders.flux.localization.Language;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.registry.registries.DynamicRegistry;
import dev.crmodders.flux.registry.registries.FreezingRegistry;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Common Class for Flux Registries
 * @author Mr-Zombii, nanobass
 */
public class FluxRegistries {

    /**
     * Runs in Phase 1 of loading
     */
    @Stable
    public static FreezingRegistry<Runnable> ON_PRE_INITIALIZE = FreezingRegistry.create();

    /**
     * Runs in Phase 3 of loading
     */
    @Stable
    public static FreezingRegistry<Runnable> ON_INITIALIZE = FreezingRegistry.create();

    /**
     * Runs in Phase 5 of loading
     */
    @Stable
    public static FreezingRegistry<Runnable> ON_POST_INITIALIZE = FreezingRegistry.create();

    /**
     * Used for Registering {@link ResourceObject}
     * This Registry will register in Phase 2 of loading
     */
    @Stable
    public static DynamicRegistry<ResourceObject> GAME_RESOURCES = DynamicRegistry.create();

    /**
     * Used for Registering {@link IModBlock} using Factories
     * This Registry will register in Phase 3 of loading
     */
    @Stable
    public static List<IFactory<IModBlock>> BLOCK_FACTORIES = new ArrayList<>();

    /**
     * Used for Registering Custom {@link FluxBlockAction} using Factories
     * This Registry will be frozen in Phase 3 of loading
     */
    @Stable
    public static FreezingRegistry<IFactory<FluxBlockAction>> BLOCK_EVENT_ACTION_FACTORIES = FreezingRegistry.create();

    @FluxInternal
    public static FreezingRegistry<Runnable> BLOCK_MODEL_FINALIZERS = FreezingRegistry.create();

    @FluxInternal
    public static FreezingRegistry<Runnable> BLOCK_FINALIZERS = FreezingRegistry.create();

    /**
     * Used by Flux to register all blocks {@link IModBlock}
     * This Registry will be frozen in Phase 3 of loading
     * Mods can look up IModBlocks using the block id here
     */
    @Stable
    public static FreezingRegistry<IModBlock> BLOCKS = FreezingRegistry.create();


    /**
     * Used for Registering {@link BasicConfig}
     */
    @Stable
    public static DynamicRegistry<BasicConfig> MOD_CONFIGS = DynamicRegistry.create();

    @FluxInternal
    public static DynamicRegistry<LanguageFile> LANGUAGE_FILES = DynamicRegistry.create();

    @FluxInternal
    public static DynamicRegistry<Language> LANGUAGES = DynamicRegistry.create();

    // LEGACY REGISTRIES

    @Legacy
    public static DynamicRegistry<FactoryFinalizer<?>> FACTORY_FINALIZERS = DynamicRegistry.create();

    @Legacy
    public static FreezingRegistry<BlockEventDataExt> BLOCK_EVENTS = FreezingRegistry.create();

    @Legacy
    public static FreezingRegistry<IBlockAction> BLOCK_EVENT_ACTIONS = FreezingRegistry.create();

    @Legacy
    public static FreezingRegistry<dev.crmodders.flux.api.v5.block.IModBlock> BLOCKS_V5 = FreezingRegistry.create();


}
