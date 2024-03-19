package io.github.crmodders.flux.mixin;

import com.badlogic.gdx.utils.Array;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.gamestates.WorldSelectionMenu;
import finalforeach.cosmicreach.ui.UIElement;
import io.github.crmodders.flux.FluxAPI;
import io.github.crmodders.flux.fabric.modinfo.FabricModInfo;
import io.github.crmodders.flux.fabric.modinfo.ModManager;
import io.github.crmodders.flux.menus.ConfigViewMenu;
import org.hjson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainMenu.class)
public abstract class FluxCapacitor extends GameState {

    @Inject(method = "create", at = @At(value = "INVOKE", target = "Lcom/badlogic/gdx/utils/Array;add(Ljava/lang/Object;)V", ordinal = 0))
    private void injectCreateAtInvoke(CallbackInfo ci, @Share("someSharedLocal") LocalRef<String> sharedLocalRef) {
        for (FabricModInfo modInfo : ModManager.getMods()) {
            FluxAPI.LOGGER.info(modInfo.modID() + ":" + modInfo.version());
        }
    }

    @Inject(method = "create", at = @At("TAIL"))
    private void injected(CallbackInfo ci) {
        boolean UseFluxMenu = FluxAPI
                .getConfig()
                .getValue(
                        "useFluxMenu",
                        new JsonObject()
                                .set("v", false)
                                .get("v")
                ).asBoolean();
        if (UseFluxMenu) {
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
                    GameState.switchToGameState(new OptionsMenu(GameState.currentGameState));
                }
            });

            newButton("Mod Configs", new UIElement(0, 110, 250, 50) {
                @Override
                public void onClick() {
                    super.onClick();
                    GameState.switchToGameState(new ConfigViewMenu(GameState.currentGameState));
                }
            });
        };

//        UIElement ConfigEditorBtn = new UIElement(
//                (float) this.uiViewport.getRightGutterWidth() / 2 - (250/2),
//                0,
//                250.0F,
//                50.0F) {
//            public void onClick() {
//                super.onClick();
//                System.exit(0);
//            }
//        };
//        ConfigEditorBtn.setText("Config Edit");
//        ConfigEditorBtn.show();
    }

    public void newButton(String name, UIElement element) {
        element.setText(name);
        element.show();
        this.uiElements.add(element);
    }
}