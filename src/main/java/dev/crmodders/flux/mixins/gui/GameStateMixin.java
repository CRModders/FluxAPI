package dev.crmodders.flux.mixins.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.gui.interfaces.GameStateInterface;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.api.renderer.UIRenderer;
import dev.crmodders.flux.api.renderer.interfaces.Component;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.UIElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameState.class)
public abstract class GameStateMixin implements GameStateInterface {

	@Shadow
	protected Viewport uiViewport;
	@Shadow
	protected OrthographicCamera uiCamera;
	@Shadow
	public Array<UIElement> uiElements;
	@Shadow
	Vector2 mouse;

	@Override
	public Viewport getViewport() {
		return uiViewport;
	}

	@Override
	public OrthographicCamera getCamera() {
		return uiCamera;
	}

	@Inject(method = "drawUIElements", at = @At(value = "INVOKE", target = "Lcom/badlogic/gdx/graphics/g2d/SpriteBatch;setProjectionMatrix(Lcom/badlogic/gdx/math/Matrix4;)V"), cancellable = true)
	private void drawCustomUIElements(CallbackInfo ci) {

		for (UIElement uiElement : uiElements) {
			Component component = (Component) uiElement;
			if (component.isDirty()) {
				component.paint(UIRenderer.uiRenderer);
			}
			((UIElementInterface) uiElement).update(uiViewport, this.mouse.x, this.mouse.y);
			component.draw(UIRenderer.uiRenderer, uiViewport);
		}
		ci.cancel();
	}

}
