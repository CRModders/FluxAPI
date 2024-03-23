package me.zombii.mod.mixins.menus;

import com.badlogic.gdx.utils.Array;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import finalforeach.cosmicreach.gamestates.WorldSelectionMenu;
import finalforeach.cosmicreach.ui.UIElement;
import io.github.crmodders.flux.FluxAPI;
import io.github.crmodders.flux.api.registries.BuiltInRegistries;
import io.github.crmodders.flux.api.registries.StaticRegistry;
import io.github.crmodders.flux.menus.ConfigViewMenu;
import me.zombii.mod.Mod;
import me.zombii.mod.gui.BetterOptionsMenu;
import org.hjson.JsonValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainMenu.class)
public class MainMenuMixin extends GameState {

    @Inject(method = "create", at = @At("TAIL"))
    private void create(CallbackInfo ci) {
        boolean UseCustomMenu = Mod
                .getConfig()
                .getValue(
                        "useCustomMenu",
                        JsonValue.valueOf(false)
                ).asBoolean();
        if (UseCustomMenu) {
            this.uiElements = new Array<>();

            FluxAPI.LOGGER.info("Rebuilding UI");
            newButton("Play", new UIElement(0, 0, 250, 50) {
                @Override
                public void onClick() {
                    super.onClick();
                    GameState.switchToGameState(new WorldSelectionMenu());
                }
            });

            newButton("Settings", new UIElement(0, 55, 250, 50) {
                @Override
                public void onClick() {
                    super.onClick();
                    GameState.switchToGameState(new BetterOptionsMenu(GameState.currentGameState));
                }
            });

            newButton("Mod Configs", new UIElement(0, 110, 250, 50) {
                @Override
                public void onClick() {
                    super.onClick();
                    GameState.switchToGameState(new ConfigViewMenu(GameState.currentGameState));
                }
            });

            newButton("Quit", new UIElement(0, 165, 250, 50) {
                @Override
                public void onClick() {
                    super.onClick();
                    System.exit(0);
                }
            });
        };

    }

    public void newButton(String name, UIElement element) {
        element.setText(name);
        element.show();
        this.uiElements.add(element);
    }

}
