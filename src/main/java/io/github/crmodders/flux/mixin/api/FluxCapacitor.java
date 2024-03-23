package io.github.crmodders.flux.mixin.api;

import com.badlogic.gdx.utils.Array;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.gamestates.WorldSelectionMenu;
import finalforeach.cosmicreach.ui.UIElement;
import io.github.crmodders.flux.FluxAPI;
import io.github.crmodders.flux.api.config.modinfo.FabricModInfo;
import io.github.crmodders.flux.api.config.modinfo.ModManager;
import io.github.crmodders.flux.api.registries.BuiltInRegistries;
import io.github.crmodders.flux.api.registries.StaticRegistry;
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
        for (FabricModInfo modInfo : ModManager.getModsFromFiles()) {
            FluxAPI.LOGGER.info(modInfo.modID() + ":" + modInfo.version());
        }
    }

    @Inject(method = "create", at = @At("TAIL"))
    private void injected(CallbackInfo ci) {
        BuiltInRegistries.VANILLA_BLOCKS = StaticRegistry.initBlockRegistry();
        BuiltInRegistries.VANILLA_BLOCKS_ACTIONS = StaticRegistry.initBlockActionRegistry();
    }

}