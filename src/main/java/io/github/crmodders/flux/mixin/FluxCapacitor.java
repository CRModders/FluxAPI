package io.github.crmodders.flux.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import finalforeach.cosmicreach.gamestates.MainMenu;
import io.github.crmodders.flux.FluxAPI;
import io.github.crmodders.flux.fabric.modinfo.FabricModInfo;
import io.github.crmodders.flux.fabric.modinfo.ModManager;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainMenu.class)
public abstract class FluxCapacitor {
    @Inject(method = "create", at = @At("HEAD"))
    private void injectCreateAtHead(CallbackInfo ci, @Share("someSharedLocal") LocalRef<String> sharedLocalRef) {
        FluxAPI.LOGGER.info("Hello from Mixin Extras!");
    }

    @Inject(method = "create", at = @At(value = "INVOKE", target = "Lcom/badlogic/gdx/utils/Array;add(Ljava/lang/Object;)V", ordinal = 0))
    private void injectCreateAtInvoke(CallbackInfo ci, @Share("someSharedLocal") LocalRef<String> sharedLocalRef) {
        for (FabricModInfo modInfo : ModManager.getMods()) {
            FluxAPI.LOGGER.info(modInfo.modID() + ":" + modInfo.version());
        }
    }

    @Inject(method = "create", at = @At("HEAD"))
    private void injected(CallbackInfo ci) {
        FluxAPI.LOGGER.info("Created!");
    }
}