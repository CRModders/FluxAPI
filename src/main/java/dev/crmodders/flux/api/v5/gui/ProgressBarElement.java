package dev.crmodders.flux.api.v5.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.v5.gui.base.BaseText;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.ui.UIRenderer;
import dev.crmodders.flux.ui.shapes.ShapeBatchBuilder;
import dev.crmodders.flux.ui.text.TextBatchBuilder;

public class ProgressBarElement extends BaseText {

    public int value;
    public int range;

    public boolean hideIfZero = false;
    public boolean prefixTranslation = true;

    @Override
    public void paint(UIRenderer renderer) {
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
        regular.color(Color.RED);
        regular.fillRect(0, 0, width * value / range, height);
        this.background = regular.build();
    }

    @Override
    public void draw(UIRenderer renderer, Viewport viewport) {
        if(hideIfZero && range == 0) {
            return;
        }
        super.draw(renderer, viewport);
    }

    @Override
    public String updateTranslation(TranslationKey key) {
        if(key == null) {
            return String.format("%d/%d", value, range);
        } else {
            if(prefixTranslation) {
                return key.getTranslated().string() + " " + String.format("%d/%d", value, range);
            } else {
                return key.getTranslated().format(String.valueOf(value), String.valueOf(range));
            }
        }
    }

    public void setProgress(int progress) {
        this.value = progress;
        updateText();
        repaint();
    }

    public void setRange(int range) {
        this.value = 0;
        this.range = range;
        updateText();
        repaint();
    }

}
