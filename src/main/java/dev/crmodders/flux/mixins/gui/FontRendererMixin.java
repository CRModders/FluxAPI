package dev.crmodders.flux.mixins.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.ui.UIRenderer;
import dev.crmodders.flux.ui.text.TextBatch;
import finalforeach.cosmicreach.ui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public class FontRendererMixin {

	@Inject(method = "getTextDimensions", at = @At("HEAD"), cancellable = true)
	private static void getTextDimensions(Viewport uiViewport, String text, Vector2 textDim, CallbackInfoReturnable<Vector2> ci) {
		TextBatch textBatch = UIRenderer.uiRenderer.createStyledText(UIRenderer.font, 18, text);
		textDim.set(textBatch.width(), textBatch.height());
		ci.setReturnValue(textDim);
	}

	@Inject(method = "drawText(Lcom/badlogic/gdx/graphics/g2d/Batch;Lcom/badlogic/gdx/utils/viewport/Viewport;Ljava/lang/String;FF)V", at = @At("HEAD"), cancellable = true)
	private static void drawText(Batch batch, Viewport uiViewport, String text, float xStart, float yStart, CallbackInfo ci) {
		TextBatch textBatch = UIRenderer.uiRenderer.createStyledText(UIRenderer.font, 18, text);
		UIRenderer.uiRenderer.drawBatch(textBatch, xStart, yStart);
		ci.cancel();
	}

}
