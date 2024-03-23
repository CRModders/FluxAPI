package io.github.crmodders.flux.mixin.flux;

import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.ui.UIElement;
import io.github.crmodders.flux.menus.ConfigViewMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsMenu.class)
public class OptionsMixin extends GameState {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        newButton("Mod Configs", new UIElement(0, 150, 250, 50) {
            @Override
            public void onClick() {
                super.onClick();
                GameState.switchToGameState(new ConfigViewMenu(GameState.currentGameState));
            }
        });
    }


    public void newButton(String name, UIElement element) {
        element.setText(name);
        element.show();
        this.uiElements.add(element);
    }

}
