package dev.crmodders.flux.mixins.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.config.BasicConfig;
import dev.crmodders.flux.api.gui.interfaces.GameStateInterface;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.ui.UIRenderer;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.gamestates.GameState;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
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

	@Inject(method = "render", at = @At("TAIL"))
	private void render(CallbackInfo ci) {
		GameStateInterface gameState = ((GameStateInterface) GameState.currentGameState);
		Viewport viewport = gameState.getViewport();

		Gdx.gl.glEnable(GL13.GL_MULTISAMPLE);
		Gdx.gl.glEnable(GL11.GL_BLEND);
		Gdx.gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		viewport.apply(false);
		UIRenderer.uiRenderer.render(gameState.getCamera().combined);
		Gdx.gl.glDisable(GL11.GL_BLEND);
		Gdx.gl.glDisable(GL13.GL_MULTISAMPLE);
	}

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
