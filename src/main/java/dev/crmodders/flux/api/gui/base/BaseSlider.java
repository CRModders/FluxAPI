package dev.crmodders.flux.api.gui.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.renderer.UIRenderer;
import dev.crmodders.flux.api.renderer.shapes.ShapeBatch;
import dev.crmodders.flux.api.renderer.shapes.ShapeBatchBuilder;
import dev.crmodders.flux.api.renderer.text.TextBatch;
import dev.crmodders.flux.api.renderer.text.TextBatchBuilder;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.ui.UIElement;

public class BaseSlider extends BaseElement {

    protected float value;
    protected float min, max;

    public String font = UIRenderer.DEFAULT_FONT;
    public float fontSize = 18f;

    public boolean soundEnabled = true;
    public boolean backgroundEnabled = true;
    public float borderThickness = 1f;
    public boolean automaticSize = false;
    public float automaticSizePadding = 16f;


    public String text = "Slider";
    public TranslationKey translation;


    protected ShapeBatch background;
    protected ShapeBatch regular;
    protected ShapeBatch highlighted;
    protected ShapeBatch clicked;
    protected TextBatch foreground;
    protected ShapeBatch knob;

    public BaseSlider(float min, float max) {
        this.min = min;
        this.max = max;
        this.value = min;
    }

    public String updateTranslation(TranslationKey key) {
        return key.getTranslated().format(String.valueOf(value));
    }

    public void updateText() {
        if(translation != null) {
            this.text = updateTranslation(translation);
        }
        repaint();
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
        TextBatchBuilder foreground = renderer.buildText();
        foreground.font(UIRenderer.font);
        foreground.fontSize(fontSize);
        foreground.color(Color.WHITE);
        foreground.style(text, true);
        this.foreground = foreground.build();

        if(automaticSize) {
            this.width = this.foreground.width() + automaticSizePadding;
            this.height = this.foreground.height() + automaticSizePadding;
        }

        ShapeBatchBuilder regular = renderer.buildShape();
        regular.color(Color.BLACK);
        regular.fillRect(0, 0, width, height);
        regular.color(Color.GRAY);
        regular.lineThickness(borderThickness);
        regular.drawRect(0, 0, width, height);
        this.regular = regular.build();

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

        ShapeBatchBuilder knob = renderer.buildShape();
        knob.color(Color.BLACK);
        knob.fillRect(0, 0, 10.0f, this.height + 8.0f);
        knob.color(Color.WHITE);
        knob.drawRect(0, 0, 10.0f, this.height + 8.0f);
        this.knob = knob.build();

        this.background = this.regular;
    }

    @Override
    public void draw(UIRenderer renderer, Viewport viewport) {
        super.draw(renderer, viewport);
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
        if(soundEnabled) {
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
        if(soundEnabled) {
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
