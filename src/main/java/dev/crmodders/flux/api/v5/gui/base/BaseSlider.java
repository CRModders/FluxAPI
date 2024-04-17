package dev.crmodders.flux.api.v5.gui.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.ui.UIRenderer;
import dev.crmodders.flux.ui.shapes.ShapeBatch;
import dev.crmodders.flux.ui.shapes.ShapeBatchBuilder;
import finalforeach.cosmicreach.settings.SoundSettings;
import finalforeach.cosmicreach.ui.UIElement;

public class BaseSlider extends BaseText {

    protected float value;
    protected float min, max;
    protected ShapeBatch regular;
    protected ShapeBatch highlighted;
    protected ShapeBatch clicked;
    protected ShapeBatch knob;

    public BaseSlider(float min, float max) {
        this.min = min;
        this.max = max;
        this.value = min;
    }

    public String updateTranslation(TranslationKey key) {
        if(key == null) {
            return String.valueOf(value);
        } else {
            return key.getTranslated().format(String.valueOf(value));
        }
    }

    public float validate(float value) {
        if(value < min) {
            value = min;
        }
        if(value > max) {
            value = max;
        }
        return value;
    }

    @Override
    public void paint(UIRenderer renderer) {
        super.paint(renderer);

        this.regular = super.background;

        ShapeBatchBuilder highlighted = renderer.buildShape();
        highlighted.color(Color.BLACK);
        highlighted.fillRect(0, 0, width, height);
        highlighted.color(Color.WHITE);
        highlighted.lineThickness(borderThickness);
        highlighted.drawRect(0, 0, width, height);
        this.highlighted = highlighted.build();

        ShapeBatchBuilder clicked = renderer.buildShape();
        clicked.color(Color.GRAY);
        clicked.fillRect(0, 0, width, height);
        clicked.color(Color.WHITE);
        clicked.lineThickness(borderThickness);
        clicked.drawRect(0, 0, width, height);
        this.clicked = clicked.build();

        this.background = this.regular;

        ShapeBatchBuilder knob = renderer.buildShape();
        knob.color(Color.BLACK);
        knob.fillRect(0, 0, 10.0f, this.height + 8.0f);
        knob.color(Color.WHITE);
        knob.drawRect(0, 0, 10.0f, this.height + 8.0f);
        this.knob = knob.build();

    }

    @Override
    public void draw(UIRenderer renderer, Viewport viewport) {
        if (!this.visible) {
            return;
        }
        float x = this.getDisplayX(viewport);
        float y = this.getDisplayY(viewport);
        if(backgroundEnabled) {
            renderer.drawBatch(background, x, y);
        }

        float ratio = (this.value - this.min) / (this.max - this.min);
        float knobX = x + ratio * this.width - 10.0f / 2.0f;
        float knobY = y - 4.0f;
        renderer.drawBatch(knob, knobX, knobY);

        renderer.drawBatch(foreground, x + (width - foreground.width()) / 2f, y + (height - foreground.height()) / 2f);
    }

    @Override
    public void onMouseEnter() {
        super.onMouseEnter();
        this.background = highlighted;
        if(soundEnabled && SoundSettings.isSoundEnabled()) {
            UIElement.onHoverSound.play();
        }
    }

    @Override
    public void onMouseExit() {
        super.onMouseExit();
        this.background = regular;
    }

    @Override
    public void onMousePressed() {
        super.onMousePressed();
        this.background = clicked;
    }

    @Override
    public void onMouseReleased() {
        super.onMouseReleased();
        this.background = regular;
        if(soundEnabled && SoundSettings.isSoundEnabled()) {
            UIElement.onClickSound.play();
        }
    }

    @Override
    public void onMouseDrag(Viewport viewport, float x, float y) {
        super.onMouseDrag(viewport, x, y);
        float x2 = getDisplayX(viewport);
        float ratio = (x - x2) / this.width;
        this.value = this.min + ratio * (this.max - this.min);
        this.value = validate(this.value);
        updateText();
    }
}
