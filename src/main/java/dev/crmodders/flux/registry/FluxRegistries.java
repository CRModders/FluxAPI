package dev.crmodders.flux.registry;

import dev.crmodders.flux.annotations.FluxInternal;
import dev.crmodders.flux.annotations.Stable;
import dev.crmodders.flux.api.v5.block.IModBlock;
import dev.crmodders.flux.api.v5.config.BasicConfig;
import dev.crmodders.flux.api.v5.generators.FactoryFinalizer;
import dev.crmodders.flux.api.v5.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.v5.resource.ResourceObject;
import dev.crmodders.flux.localization.Language;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.registry.registries.DynamicRegistry;
import dev.crmodders.flux.registry.registries.FreezingRegistry;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;

/**
 * Common Class for Flux Registries
 * @author Mr-Zombii, nanobass
 */
public class FluxRegistries {

    /**
     * Used for Registering {@link ResourceObject}
     * This Registry will register in Phase 3 of loading
     */
    @Stable
    public static DynamicRegistry<ResourceObject> GAME_RESOURCES = DynamicRegistry.create();

    /**
     * Used for Registering Custom {@link IModBlock}
     * This Registry will be frozen in Phase 4 of loading
     */
    @Stable
    public static FreezingRegistry<IModBlock> BLOCKS = FreezingRegistry.create();

    /**
     * Used for Registering Custom {@link IBlockAction}
     * This Registry will be frozen in Phase 5 of loading
     */
    @Stable
    public static FreezingRegistry<IBlockAction> BLOCK_EVENT_ACTIONS = FreezingRegistry.create();

    /**
     * Used for Registering Custom {@link BlockEventDataExt}
     * This Registry will be frozen in Phase 6 of loading
     */
    @Stable
    public static FreezingRegistry<BlockEventDataExt> BLOCK_EVENTS = FreezingRegistry.create();

    /**
     * Used for Registering {@link FactoryFinalizer}
     * This Registry will register in Phase 7 of loading
     */
    @Stable
    public static DynamicRegistry<FactoryFinalizer<?>> FACTORY_FINALIZERS = DynamicRegistry.create();

    @FluxInternal
    public static DynamicRegistry<LanguageFile> LANGUAGE_FILES = DynamicRegistry.create();

    @FluxInternal
    public static DynamicRegistry<Language> LANGUAGES = DynamicRegistry.create();


    /**
     * Used for Registering {@link BasicConfig}
     */
    @Stable
    public static DynamicRegistry<BasicConfig> MOD_CONFIGS = DynamicRegistry.create();
}
