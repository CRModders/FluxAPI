package dev.crmodders.flux.api.gui.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.ui.UIRenderer;
import dev.crmodders.flux.ui.shapes.ShapeBatch;
import dev.crmodders.flux.ui.shapes.ShapeBatchBuilder;
import dev.crmodders.flux.ui.text.TextBatch;
import dev.crmodders.flux.ui.text.TextBatchBuilder;

public class BaseText extends BaseElement{

    public String font = UIRenderer.DEFAULT_FONT;
    public float fontSize = 18f;

    public boolean soundEnabled = true;
    public boolean backgroundEnabled = true;
    public float borderThickness = 1f;
    public boolean automaticSize = false;
    public float automaticSizePadding = 16f;


    public String text = "";
    public TranslationKey translation;


    protected ShapeBatch background;
    protected TextBatch foreground;

    public String updateTranslation(TranslationKey key) {
        if(key == null) {
            return text;
        } else {
            return key.getTranslated().string();
        }
    }

    public void updateText() {
        this.text = updateTranslation(translation);
        repaint();
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

        ShapeBatchBuilder regular = new ShapeBatchBuilder();
        regular.color(Color.BLACK);
        regular.fillRect(0, 0, width, height);
        regular.color(Color.GRAY);
        regular.lineThickness(borderThickness);
        regular.drawRect(0, 0, width, height);
        this.background = regular.build();
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
            background.render(renderer, x, y);
        }
        foreground.render(renderer, x + (width - foreground.width()) / 2f, y + (height - foreground.height()) / 2f);
    }



}
