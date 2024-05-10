package dev.crmodders.flux.mixins.gui;

import dev.crmodders.flux.api.config.BasicConfig;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.BlockGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockGame.class, priority = 2000)
public class BlockGameMixin {

	@Unique
	private static Logger logger = LoggerFactory.getLogger("FluxAPI / BlockGameInfo");

	@Inject(method = "dispose", at = @At("HEAD"))
	private void dispose(CallbackInfo ci) {
		AccessableRegistry<BasicConfig> access = FluxRegistries.MOD_CONFIGS.access();
		for(Identifier id : access.getRegisteredNames()) {
			BasicConfig config = access.get(id);
			try {
				config.save();
			} catch (Exception e) {

				logger.error("Failed to save config {}", id);
			}
		}
	}

}
