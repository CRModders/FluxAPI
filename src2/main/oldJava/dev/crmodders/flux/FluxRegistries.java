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
import org.greenrobot.eventbus.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

/**
 * Common Class for Flux Registries
 * @author Mr-Zombii, nanobass
 */
public class FluxRegistries {

    @Stable
    public static final EventBus EVENT_BUS = EventBus.builder().sendNoSubscriberEvent(false).logNoSubscriberMessages(false).logger(new SLF4JEventBusLogger()).build();

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

    @FluxInternal
    private static class SLF4JEventBusLogger implements Logger {
        private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("FluxAPI / EventBus");

        @Override
        public void log(Level level, String s) {
            if(level == Level.INFO) {
                LOGGER.info(s);
            } else if(level == Level.WARNING) {
                LOGGER.warn(s);
            } else if(level == Level.SEVERE) {
                LOGGER.error(s);
            }
        }

        @Override
        public void log(Level level, String s, Throwable t) {
            if(level == Level.INFO) {
                LOGGER.info(s, t);
            } else if(level == Level.WARNING) {
                LOGGER.warn(s, t);
            } else if(level == Level.SEVERE) {
                LOGGER.error(s, t);
            }
        }
    }

}
