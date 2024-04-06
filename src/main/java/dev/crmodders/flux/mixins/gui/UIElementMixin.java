package dev.crmodders.flux.mixins.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.api.renderer.UIRenderer;
import dev.crmodders.flux.api.renderer.text.TextBatch;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.UIElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(UIElement.class)
public abstract class UIElementMixin {

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
    abstract float getDisplayX(Viewport uiViewport);

    @Shadow
    abstract float getDisplayY(Viewport uiViewport);

    @Overwrite
    public void drawText(Viewport uiViewport, SpriteBatch batch) {
        if (this.shown && this.text != null && !this.text.isEmpty()) {
            float x = this.getDisplayX(uiViewport);
            float y = this.getDisplayY(uiViewport);

            TextBatch tb = UIRenderer.uiRenderer.createText(UIRenderer.font, 18f, text, Color.WHITE);
            x += this.w / 2.0F - tb.width() / 2.0F;
            y += this.h / 2.0F - tb.height() / 2.0F;

            UIRenderer.uiRenderer.drawBatch(tb, x, y);
        }
    }

}
