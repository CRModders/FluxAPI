package dev.crmodders.flux.registry;

import dev.crmodders.flux.annotations.FluxInternal;
import dev.crmodders.flux.annotations.Stable;
import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.api.config.BasicConfig;
import dev.crmodders.flux.api.factories.IModBlockFactory;
import dev.crmodders.flux.api.generators.FactoryFinalizer;
import dev.crmodders.flux.api.generators.data.blockevent.BlockEventDataExt;
import dev.crmodders.flux.api.resource.ResourceObject;
import dev.crmodders.flux.localization.Language;
import dev.crmodders.flux.localization.LanguageFile;
import dev.crmodders.flux.registry.registries.DynamicRegistry;
import dev.crmodders.flux.registry.registries.FreezingRegistry;
import finalforeach.cosmicreach.blockevents.IBlockEventAction;

import java.util.ArrayList;
import java.util.List;

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

    @Stable
    public static List<IModBlockFactory> BLOCK_FACTORIES = new ArrayList<>();

    /**
     * Used for Registering Custom {@link IModBlock}
     * This Registry will be frozen in Phase 4 of loading
     */
    @Stable
    public static FreezingRegistry<IModBlock> BLOCKS = FreezingRegistry.create();

    /**
     * Used for Registering Custom {@link IBlockEventAction}
     * This Registry will be frozen in Phase 5 of loading
     */
    @Stable
    public static FreezingRegistry<IBlockEventAction> BLOCK_EVENT_ACTIONS = FreezingRegistry.create();

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
