package dev.crmodders.flux.mixins.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.gui.base.BaseElement;
import dev.crmodders.flux.api.gui.base.BaseText;
import dev.crmodders.flux.api.gui.interfaces.GameStateCache;
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
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Mixin(GameState.class)
public abstract class GameStateMixin implements GameStateInterface, GameStateCache {

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

	@Inject(method = "switchToGameState", at = @At(value = "HEAD"))
	private static void cleanUpCustomUIElements(CallbackInfo ci) {
		if(GameState.currentGameState != null) {
			for(Component component : ((GameStateInterface) GameState.currentGameState).getComponents()) {
				component.deactivate();
			}
		}
	}

	private static final List<GameState> stack = new ArrayList<>();

	private static int doReverseSearch(GameState state) {
		for(int i = stack.size() - 1; i >= 0; i--) {
			if(stack.get(i) == state) {
				return i;
			}
		}
		return -1;
	}

	@Inject(method = "switchToGameState", at = @At("HEAD"))
	private static void switchToGameState(GameState next, CallbackInfo ci) {
		GameState current = GameState.currentGameState;
		int back = doReverseSearch(next);
		if(back != -1) {
			// found the same game state in the stack, go back to it
			if (stack.size() > back) {
				stack.subList(back, stack.size()).clear();
			}
		}

		stack.add(next);
	}

	@Override
	public List<GameState> getAllLoadedGameStates() {
		return Collections.unmodifiableList(stack);
	}

	@Override
	public void invalidateCachedAssets() {
		for(GameState state : stack) {
            for (UIElement uiElement : state.uiElements) {
                uiElement.updateText();
            }
			for(Component element : ((GameStateInterface) state).getComponents()) {
				if(element instanceof BaseElement base) {
					base.repaint();
				}
				if(element instanceof BaseText text) {
					text.updateText();
				}
			}
		}
	}

}
