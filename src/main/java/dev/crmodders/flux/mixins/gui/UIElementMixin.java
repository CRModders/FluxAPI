package dev.crmodders.flux.mixins.gui;

import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.api.renderer.UIRenderer;
import dev.crmodders.flux.api.renderer.interfaces.Component;
import dev.crmodders.flux.api.renderer.shapes.ShapeBatch;
import dev.crmodders.flux.api.renderer.shapes.ShapeBatchBuilder;
import dev.crmodders.flux.api.renderer.text.TextBatch;
import dev.crmodders.flux.api.renderer.text.TextBatchBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;

import finalforeach.cosmicreach.audio.SoundManager;
import finalforeach.cosmicreach.ui.UIElement;

@Mixin(UIElement.class)
public abstract class UIElementMixin implements Component, UIElementInterface {

	@Shadow
	String text;
	@Shadow
	public float x;
	@Shadow
	public float y;
	@Shadow
	public float w;
	@Shadow
	public float h;
	@Shadow
	boolean shown;
	@Shadow
	boolean hoveredOver;
	@Shadow
	public static UIElement currentlyHeldElement;
	@Shadow
	public boolean isHeld;

	private boolean dirty;

	private ShapeBatch background;
	private ShapeBatch regular;
	private ShapeBatch highlighted;
	private ShapeBatch clicked;
	private TextBatch foreground;

	@Shadow
	abstract float getDisplayX(Viewport uiViewport);

	@Override
	public float displayX(Viewport uiViewport) {
		return getDisplayX(uiViewport);
	}

	@Shadow
	abstract float getDisplayY(Viewport uiViewport);

	@Override
	public float displayY(Viewport uiViewport) {
		return getDisplayY(uiViewport);
	}

	@Shadow
	protected abstract boolean isHoveredOver(Viewport viewport, float x, float y);

	@Shadow
	protected abstract void onCreate();

	@Shadow
	protected abstract void onClick();

	@Shadow
	protected abstract void onMouseDown();

	@Shadow
	protected abstract void onMouseUp();

	@Inject(method = "setText", at = @At("TAIL"))
	private void onSetText(CallbackInfo ci) {
		dirty = true;
	}

	@Inject(method = "updateText", at=@At("TAIL"))
	private void onUpdateText(CallbackInfo ci) { dirty = true; }

	@Override
	public void update(Viewport uiViewport, float mouseX, float mouseY) {
		if (!this.shown) {
			return;
		}
		this.background = regular;
		if (this == (Object) currentlyHeldElement && Gdx.input.isButtonPressed(0)) {
			this.isHeld = true;
		}
		if (this.isHeld && !Gdx.input.isButtonPressed(0)) {
			this.isHeld = false;
			this.onMouseUp();
		}
		if (this.isHoveredOver(uiViewport, mouseX, mouseY)) {
			if (!this.hoveredOver) {
				SoundManager.playSound(UIElement.onHoverSound);
				this.hoveredOver = true;
				this.background = highlighted;
			}
			if (Gdx.input.isButtonJustPressed(0)) {
				currentlyHeldElement = (UIElement) (Object) this;
				this.onMouseDown();
			}
			if ((Object) currentlyHeldElement == this) {
				this.background = clicked;
			}
		} else {
			this.hoveredOver = false;
			if ((Object) currentlyHeldElement == this && !Gdx.input.isButtonPressed(0)) {
				currentlyHeldElement = null;
			}
		}
		if ((Object) currentlyHeldElement == this && !Gdx.input.isButtonPressed(0)) {
			this.background = clicked;
			currentlyHeldElement = null;
			this.onClick();
			SoundManager.playSound(UIElement.onClickSound);
		}
	}

	@Override
	public void paint(UIRenderer renderer) {
		ShapeBatchBuilder regular = renderer.buildShape();
		regular.color(Color.BLACK);
		regular.fillRect(0, 0, w, h);
		regular.color(Color.GRAY);
		regular.drawRect(0, 0, w, h);
		this.regular = regular.build();

		ShapeBatchBuilder highlighted = renderer.buildShape();
		highlighted.color(Color.BLACK);
		highlighted.fillRect(0, 0, w, h);
		highlighted.color(Color.WHITE);
		highlighted.drawRect(0, 0, w, h);
		this.highlighted = highlighted.build();

		ShapeBatchBuilder clicked = renderer.buildShape();
		clicked.color(Color.GRAY);
		clicked.fillRect(0, 0, w, h);
		clicked.color(Color.WHITE);
		clicked.drawRect(0, 0, w, h);
		this.clicked = clicked.build();

		TextBatchBuilder foreground = renderer.buildText();
		foreground.color(Color.WHITE);
		foreground.style(text);
		this.foreground = foreground.build();
	}

	@Override
	public void draw(UIRenderer renderer, Viewport viewport) {
		if (!this.shown) {
			return;
		}
		float x = this.getDisplayX(viewport);
		float y = this.getDisplayY(viewport);
		renderer.drawBatch(hoveredOver ? highlighted : background, x, y);
		renderer.drawBatch(foreground, x + (w - foreground.width()) / 2f, y + (h - foreground.height()) / 2f);
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

}
