package dev.crmodders.flux.mixins.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.gui.interfaces.GameStateInterface;
import dev.crmodders.flux.api.renderer.UIRenderer;
import dev.crmodders.flux.api.renderer.interfaces.Component;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.UIElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(GameState.class)
public abstract class GameStateMixin implements GameStateInterface {

	@Shadow
	protected Viewport uiViewport;
	@Shadow
	protected OrthographicCamera uiCamera;
	@Shadow
	Vector2 mouse;
	private List<Component> components = new ArrayList<>();

	@Override
	public Viewport getViewport() {
		return uiViewport;
	}

	@Override
	public OrthographicCamera getCamera() {
		return uiCamera;
	}

	@Override
	public List<Component> getComponents() {
		return components;
	}

	@Inject(method = "drawUIElements", at = @At(value = "INVOKE", target = "Lcom/badlogic/gdx/graphics/g2d/SpriteBatch;setProjectionMatrix(Lcom/badlogic/gdx/math/Matrix4;)V"))
	private void drawCustomUIElements(CallbackInfo ci) {
		for (Component component : components) {
			if (component.isDirty()) {
				component.paint(UIRenderer.uiRenderer);
			}
			component.update(UIRenderer.uiRenderer, uiViewport, mouse);
			component.draw(UIRenderer.uiRenderer, uiViewport);
		}
	}

}
