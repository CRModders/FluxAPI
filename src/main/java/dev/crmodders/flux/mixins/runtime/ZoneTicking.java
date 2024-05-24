package dev.crmodders.flux.mixins.runtime;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import dev.crmodders.flux.api.v5.entities.ZoneBlockEntityPair;
import dev.crmodders.flux.api.v5.entities.api.IFluxBlockEntity;
import dev.crmodders.flux.registry.FluxRegistries;
import dev.crmodders.flux.util.PrivUtils;
import finalforeach.cosmicreach.gamestates.InGame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(InGame.class)
public class ZoneTicking {

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/world/Zone;runScheduledTriggers()V", shift = At.Shift.AFTER))
    private void update(float deltaTime, CallbackInfo ci) {
        Arrays.stream(FluxRegistries.BLOCK_ENTITIES.access().getRegisteredNames()).forEach(identifier -> {
            ZoneBlockEntityPair pair = FluxRegistries.BLOCK_ENTITIES.access().get(identifier);
            if (pair.zone().zoneId.equals(InGame.getLocalPlayer().getZone(InGame.world).zoneId)) {
                pair.entity().onTick();
            }
        });
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UI;render()V", shift = At.Shift.BEFORE))
    private void render(float deltaTime, CallbackInfo ci) {
        Arrays.stream(FluxRegistries.BLOCK_ENTITIES.access().getRegisteredNames()).forEach(identifier -> {
            ZoneBlockEntityPair pair = FluxRegistries.BLOCK_ENTITIES.access().get(identifier);
            if (pair.zone().zoneId.equals(InGame.getLocalPlayer().getZone(InGame.world).zoneId)) {
                try {
                    pair.entity().renderEntity((PerspectiveCamera) PrivUtils.getPrivField(InGame.class, "rawWorldCamera"));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
