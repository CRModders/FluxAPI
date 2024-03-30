package dev.crmodders.flux.mixins.localization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.gui.interfaces.GameStateInterface;
import dev.crmodders.flux.api.renderer.UIRenderer;
import dev.crmodders.flux.api.renderer.text.TextBatchBuilder;
import dev.crmodders.flux.api.toast.ToastManager;
import dev.crmodders.flux.api.toast.ToastRenderable;
import dev.crmodders.flux.localization.TranslationApi;
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

@Mixin(value = BlockGame.class)
public class BlockGameMixin {

	@Inject(method="create", at=@At("TAIL"))
	private void create(CallbackInfo ci) {
		TranslationApi.discoverLanguages();
	}

}
