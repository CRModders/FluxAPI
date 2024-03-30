package dev.crmodders.flux.mixins.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.gui.interfaces.GameStateInterface;
import dev.crmodders.flux.api.renderer.UIRenderer;
import dev.crmodders.flux.api.renderer.text.TextBatchBuilder;
import dev.crmodders.flux.api.toast.ToastManager;
import dev.crmodders.flux.api.toast.ToastRenderable;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockGame.class, priority = 2000)
public class BlockGameMixin {

	@Inject(method = "render", at = @At("TAIL"))
	private void render(CallbackInfo ci) {
		TextBatchBuilder batch = UIRenderer.uiRenderer.buildText(UIRenderer.font, 18);
		for (ToastRenderable toast : ToastManager.getActive()) {
			batch.alpha(Math.min(1.0f, toast.toastTimer));
			batch.append(String.format("%s [%.1f]", toast.getToast(), toast.toastTimer));
			batch.append('\n');
			toast.toastTimer -= Gdx.graphics.getDeltaTime();
		}
		ToastManager.removeInactive();

		GameStateInterface gameState = ((GameStateInterface) GameState.currentGameState);
		Viewport viewport = gameState.getViewport();
		UIRenderer.uiRenderer.drawBatch(batch.build(), viewport, -25f, 0f, HorizontalAnchor.RIGHT_ALIGNED, VerticalAnchor.TOP_ALIGNED);

		Gdx.gl.glEnable(GL13.GL_MULTISAMPLE);
		Gdx.gl.glEnable(GL11.GL_BLEND);
		Gdx.gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		viewport.apply(false);
		UIRenderer.uiRenderer.render(gameState.getCamera().combined);
		Gdx.gl.glDisable(GL11.GL_BLEND);
		Gdx.gl.glDisable(GL13.GL_MULTISAMPLE);

	}

}
