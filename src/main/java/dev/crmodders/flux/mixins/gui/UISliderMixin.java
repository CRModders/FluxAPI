package dev.crmodders.flux.mixins.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.api.renderer.UIRenderer;
import dev.crmodders.flux.api.renderer.shapes.ShapeBatch;
import dev.crmodders.flux.api.renderer.shapes.ShapeBatchBuilder;
import finalforeach.cosmicreach.ui.UISlider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(UISlider.class)
public abstract class UISliderMixin extends UIElementMixin {

	@Shadow
	private float min;
	@Shadow
	private float max;
	@Shadow
	protected float currentValue;

	private ShapeBatch knob;

	@Shadow
	public abstract void validate();

	@Override
	public void update(Viewport uiViewport, float mouseX, float mouseY) {
		super.update(uiViewport, mouseX, mouseY);
		if (this.isHeld) {
			float sx = mouseX;
			float x = ((UIElementInterface) this).displayX(uiViewport);
			float ratio = (sx - x) / this.w;
			this.currentValue = this.min + ratio * (this.max - this.min);
			this.validate();
		}
	}

	@Override
	public void paint(UIRenderer renderer) {
		super.paint(renderer);
		ShapeBatchBuilder knob = renderer.buildShape();
		knob.color(Color.BLACK);
		knob.fillRect(0, 0, 10.0f, this.h + 8.0f);
		knob.color(Color.WHITE);
		knob.drawRect(0, 0, 10.0f, this.h + 8.0f);
		this.knob = knob.build();
	}

	@Override
	public void draw(UIRenderer renderer, Viewport viewport) {
		if (!this.shown) {
			return;
		}
		float x = this.getDisplayX(viewport);
		float y = this.getDisplayY(viewport);
		if(borderEnabled) {
			renderer.drawBatch(hoveredOver ? highlighted : background, x, y);
		}

		float ratio = (this.currentValue - this.min) / (this.max - this.min);
		float knobX = x + ratio * this.w - 10.0f / 2.0f;
		float knobY = y - 4.0f;
		renderer.drawBatch(knob, knobX, knobY);

		renderer.drawBatch(foreground, x + (w - foreground.width()) / 2f, y + (h - foreground.height()) / 2f);
	}

}
