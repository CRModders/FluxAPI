package dev.crmodders.flux;

import dev.crmodders.flux.annotations.Experimental;
import dev.crmodders.flux.annotations.FluxInternal;
import dev.crmodders.flux.annotations.Stable;
import dev.crmodders.flux.block.FluxBlockAction;
import dev.crmodders.flux.block.IModBlock;
import dev.crmodders.flux.factories.IFactory;
import dev.crmodders.flux.localization.ILanguageFile;
import dev.crmodders.flux.localization.Language;
import dev.crmodders.flux.registries.DynamicRegistry;
import dev.crmodders.flux.registries.FreezingRegistry;
import org.greenrobot.eventbus.EventBus;

/**
 * Common Class for Flux Registries
 * @author Mr-Zombii, nanobass
 */
public class FluxRegistries {

    @Stable
    public static final EventBus EVENT_BUS = EventBus.builder().build();

    @Stable
    public static FreezingRegistry<Runnable> ON_PRE_INITIALIZE = FreezingRegistry.create();

    @Stable
    public static FreezingRegistry<Runnable> ON_INITIALIZE = FreezingRegistry.create();

    @Stable
    public static FreezingRegistry<Runnable> ON_POST_INITIALIZE = FreezingRegistry.create();

    @Experimental
    public static FreezingRegistry<IFactory<FluxBlockAction>> BLOCK_EVENT_ACTION_FACTORIES = FreezingRegistry.create();

    /**
     * Used by Flux to register all blocks {@link IModBlock}
     * This Registry is used by flux and is read only to Mods.
     * Mods can look up IModBlocks using the block id here
     */
    @Stable
    public static FreezingRegistry<IModBlock> BLOCKS = FreezingRegistry.create();

    // INTERNAL

    @FluxInternal
    public static FreezingRegistry<Runnable> BLOCK_MODEL_FINALIZERS = FreezingRegistry.create();

    @FluxInternal
    public static FreezingRegistry<Runnable> BLOCK_FINALIZERS = FreezingRegistry.create();

    @FluxInternal
    public static DynamicRegistry<ILanguageFile> LANGUAGE_FILES = DynamicRegistry.create();

    @FluxInternal
    public static DynamicRegistry<Language> LANGUAGES = DynamicRegistry.create();

}
