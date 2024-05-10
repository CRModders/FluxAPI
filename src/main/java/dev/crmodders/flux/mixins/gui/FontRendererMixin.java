package dev.crmodders.flux.mixins.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.ui.UIRenderer;
import dev.crmodders.flux.ui.text.TextBatch;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public class FontRendererMixin {

	private static UIRenderer uiRenderer = new UIRenderer(GameState.batch);

	@Inject(method = "getTextDimensions", at = @At("HEAD"), cancellable = true)
	private static void getTextDimensions(Viewport uiViewport, String text, Vector2 textDim, CallbackInfoReturnable<Vector2> ci) {
		TextBatch textBatch = uiRenderer.createStyledText(UIRenderer.font, 18, text);
		textDim.set(textBatch.width(), textBatch.height());
		ci.setReturnValue(textDim);
	}

	@Inject(method = "drawText(Lcom/badlogic/gdx/graphics/g2d/SpriteBatch;Lcom/badlogic/gdx/utils/viewport/Viewport;Ljava/lang/String;FF)V", at = @At("HEAD"), cancellable = true)
	private static void drawText(SpriteBatch batch, Viewport uiViewport, String text, float xStart, float yStart, CallbackInfo ci) {
		if(batch != uiRenderer.batch) uiRenderer.changeSpriteBatch(batch);
		TextBatch textBatch = uiRenderer.createStyledText(UIRenderer.font, 18, text);
		textBatch.render(uiRenderer, xStart, yStart);
		ci.cancel();
	}

}
