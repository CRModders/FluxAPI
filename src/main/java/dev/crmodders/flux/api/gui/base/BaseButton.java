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

public class BaseButton extends BaseText {

    protected ShapeBatch regular;
    protected ShapeBatch highlighted;
    protected ShapeBatch clicked;

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

        this.dirty = false;
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
}
