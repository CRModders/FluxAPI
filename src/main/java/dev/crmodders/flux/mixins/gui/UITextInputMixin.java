package dev.crmodders.flux.mixins.gui;

import dev.crmodders.flux.api.gui.interfaces.UIElementInterface;
import dev.crmodders.flux.api.renderer.UIRenderer;
import finalforeach.cosmicreach.ui.UITextInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(UITextInput.class)
public abstract class UITextInputMixin extends UIElementMixin{

    @Shadow
    protected abstract void updateText();

    @Override
    public void paint(UIRenderer renderer) {
        boolean active = equals(UITextInput.activeElement);
        setStyleEnabled(!active);
        updateText();
        super.paint(renderer);
    }
}
