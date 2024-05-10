package dev.crmodders.flux.api.gui.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.ui.UIRenderer;
import dev.crmodders.flux.ui.image.ImageBatch;
import dev.crmodders.flux.ui.image.ImageBatchBuilder;
import dev.crmodders.flux.ui.shapes.ShapeBatch;
import dev.crmodders.flux.ui.shapes.ShapeBatchBuilder;
import finalforeach.cosmicreach.settings.SoundSettings;
import finalforeach.cosmicreach.ui.UIElement;

public class BaseButton extends BaseText {

    public Texture image;
    public float iconSize = fontSize;
    public float iconPadding = 4f;
    public float iconRotation = 0f;

    protected ImageBatch icon;
    protected ShapeBatch regular;
    protected ShapeBatch highlighted;
    protected ShapeBatch clicked;

    @Override
    public void paint(UIRenderer renderer) {
        super.paint(renderer);

        this.regular = super.background;

        ShapeBatchBuilder highlighted = new ShapeBatchBuilder();
        highlighted.color(Color.BLACK);
        highlighted.fillRect(0, 0, width, height);
        highlighted.color(Color.WHITE);
        highlighted.lineThickness(borderThickness);
        highlighted.drawRect(0, 0, width, height);
        this.highlighted = highlighted.build();

        ShapeBatchBuilder clicked = new ShapeBatchBuilder();
        clicked.color(Color.GRAY);
        clicked.fillRect(0, 0, width, height);
        clicked.color(Color.WHITE);
        clicked.lineThickness(borderThickness);
        clicked.drawRect(0, 0, width, height);
        this.clicked = clicked.build();

        this.background = this.regular;

        if(image != null) {
            ImageBatchBuilder icon = new ImageBatchBuilder();
            icon.drawImage(image, 0, 0, iconSize, iconSize, iconRotation);
            this.icon = icon.build();
        }

        this.dirty = false;
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

        if(icon == null) {
            float left = (width - foreground.width()) / 2f;
            float top = (height - foreground.height()) / 2f;
            foreground.render(renderer, x + left, y + top);
        } else {
            float iconWidth = iconSize + iconPadding * 2f;
            float iconHeight = iconSize + iconPadding * 2f;

            float left1 = (width - iconWidth - foreground.width()) / 2f + iconWidth;
            float top1 = (height - foreground.height()) / 2f;
            foreground.render(renderer, x + left1, y + top1);

            float left2 = (width - iconWidth - foreground.width()) / 2f + iconPadding;
            float top2 = (height - iconHeight) / 2f + iconPadding;
            icon.render(renderer, x + left2, y + top2);
        }

    }

    public void setIcon(Texture icon, float size, float padding) {
        this.image = icon;
        this.iconSize = size;
        this.iconPadding = padding;
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
}
