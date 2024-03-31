package dev.crmodders.flux.mixins.gamestate;

import dev.crmodders.flux.localization.TranslationKey;
import dev.crmodders.flux.menus.ConfigViewMenu;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import finalforeach.cosmicreach.ui.UIElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainMenu.class)
public class MainMenuMixin extends GameState {

    @Unique
    private static final TranslationKey TEXT_MOD_CONFIGS = new TranslationKey("fluxapi:main_menu.config_menu");

    @Inject(method = "create", at = @At("TAIL"))
    public void create(CallbackInfo ci) {
        UIElement configButton = new UIElement(350.0F, 150.0F, 130.0F, 50.0F) {
            public void onClick() {
                super.onClick();
                GameState.switchToGameState(new ConfigViewMenu(GameState.currentGameState));
            }
        };
        configButton.setText(TEXT_MOD_CONFIGS.getTranslated().string());
        configButton.show();
        this.uiElements.add(configButton);
    }

}
